package typo.dsl

import cats.data.NonEmptyList
import cats.syntax.foldable.*
import doobie.ConnectionIO
import doobie.free.connection.delay
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.{Put, Read, fragments}

import java.util.concurrent.atomic.AtomicInteger

trait UpdateBuilder[Fields[_], Row] {
  protected def params: UpdateParams[Fields, Row]
  protected def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row]

  final def whereStrict(v: Fields[Row] => SqlExpr[Boolean, Required, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.where(v))

  final def where[N[_]: Nullability](v: Fields[Row] => SqlExpr[Boolean, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.where(f => v(f).?.coalesce(false)))

  final def setValue[N[_], T](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: N[T])(implicit P: Put[N[T]]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, _ => SqlExpr.Const[T, N, Row](value, P)))

  final def setComputedValue[T, N[_]](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: SqlExpr.FieldLikeNotId[T, N, Row] => SqlExpr[T, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, fields => value(col(fields))))

  final def setComputedValueFromRow[T, N[_]](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: Fields[Row] => SqlExpr[T, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, value))

  def sql(returning: Boolean): Option[Fragment]
  def execute: ConnectionIO[Int]
  def executeReturnChanged: ConnectionIO[List[Row]]
}

object UpdateBuilder {
  def apply[Fields[_], Row](name: String, structure: Structure.Relation[Fields, ?, Row], rowParser: Read[Row]): UpdateBuilderSql[Fields, Row] =
    UpdateBuilderSql(name, structure, rowParser, UpdateParams.empty)

  case class UpdateBuilderSql[Fields[_], Row](
      name: String,
      structure: Structure.Relation[Fields, ?, Row],
      read: Read[Row],
      params: UpdateParams[Fields, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = sqlParams)

    def mkSql(counter: AtomicInteger, returning: Boolean): Fragment = {
      List[Option[Fragment]](
        Some(fr"update ${Fragment.const(name)}"),
        NonEmptyList.fromList(params.setters) match {
          case None =>
            sys.error("you must specify a columns to set. use `set` method")
          case Some(setters) =>
            val setFragments = setters.map { setter =>
              val fieldExpr = setter.col(structure.fields)
              val valueExpr = setter.value(structure.fields)
              fr"${fieldExpr.render(counter)} = ${valueExpr.render(counter)}${fieldExpr.sqlWriteCast.fold(Fragment.empty)(cast => Fragment.const(s"::$cast"))}"
            }
            Some(fragments.set(setFragments))
        },
        NonEmptyList.fromList(params.where).map { wheres =>
          fragments.whereAnd(
            wheres.map { where =>
              where(structure.fields).render(counter)
            }
          )
        },
        if (returning) {
          val colFragments = fragments.comma(
            NonEmptyList.fromListUnsafe(structure.columns).map { col =>
              Fragment.const(col.sqlReadCast.foldLeft("\"" + col.value + "\"") { case (acc, cast) => s"$acc::$cast" })
            }
          )
          Some(fr"returning $colFragments")
        } else None
      ).flatten.intercalate(fr" ")
    }

    override def sql(returning: Boolean): Option[Fragment] = {
      Some(mkSql(new AtomicInteger(1), returning))
    }

    override def execute: ConnectionIO[Int] =
      mkSql(new AtomicInteger(0), returning = false).update.run

    override def executeReturnChanged: ConnectionIO[List[Row]] = {
      mkSql(new AtomicInteger(0), returning = true).query(using read).to[List]
    }
  }
  case class UpdateBuilderMock[Id, Fields[_], Row](
      params: UpdateParams[Fields, Row],
      fields: Fields[Row],
      map: scala.collection.mutable.Map[Id, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = sqlParams)

    override def sql(returning: Boolean): Option[Fragment] =
      None

    override def execute: ConnectionIO[Int] =
      executeReturnChanged.map(_.size)

    override def executeReturnChanged: ConnectionIO[List[Row]] = delay {
      val changed = List.newBuilder[Row]
      map.foreach { case (id, row) =>
        if (params.where.forall(w => w(fields).eval(row))) {
          val newRow = params.setters.foldLeft(row) { case (row, set) => set.transform(fields, row) }
          map.update(id, newRow)
          changed += newRow
        }
      }
      changed.result()
    }
  }
}
