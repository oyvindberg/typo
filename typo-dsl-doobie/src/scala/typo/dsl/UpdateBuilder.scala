package typo.dsl

import cats.data.NonEmptyList
import cats.syntax.foldable.*
import doobie.ConnectionIO
import doobie.free.connection.delay
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.{Put, Read, Write, fragments}

trait UpdateBuilder[Fields, Row] {
  protected def params: UpdateParams[Fields, Row]
  protected def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row]

  final def whereStrict(v: Fields => SqlExpr[Boolean, Required]): UpdateBuilder[Fields, Row] =
    withParams(params.where(v))

  final def where[N[_]: Nullability](v: Fields => SqlExpr[Boolean, N]): UpdateBuilder[Fields, Row] =
    withParams(params.where(f => v(f).?.coalesce(false)))

  final def setValue[N[_], T](col: Fields => SqlExpr.FieldLikeNotId[T, N, Row])(value: N[T])(implicit P: Put[T], W: Write[N[T]]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, _ => SqlExpr.Const[T, N](value, P, W)))

  final def setComputedValue[T, N[_]](col: Fields => SqlExpr.FieldLikeNotId[T, N, Row])(value: SqlExpr.FieldLikeNotId[T, N, Row] => SqlExpr[T, N]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, fields => value(col(fields))))

  final def setComputedValueFromRow[T, N[_]](col: Fields => SqlExpr.FieldLikeNotId[T, N, Row])(value: Fields => SqlExpr[T, N]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, value))

  def sql(returning: Boolean): Option[Fragment]
  def execute: ConnectionIO[Int]
  def executeReturnChanged: ConnectionIO[List[Row]]
}

object UpdateBuilder {
  def apply[Fields, Row](name: String, structure: Structure.Relation[Fields, Row], rowParser: Read[Row]): UpdateBuilderSql[Fields, Row] =
    UpdateBuilderSql(name, structure, rowParser, UpdateParams.empty)

  final case class UpdateBuilderSql[Fields, Row](
      name: String,
      structure: Structure.Relation[Fields, Row],
      read: Read[Row],
      params: UpdateParams[Fields, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = sqlParams)

    def mkSql(returning: Boolean): Fragment =
      mkSql(RenderCtx.Empty, returning)

    def mkSql(ctx: RenderCtx, returning: Boolean): Fragment = {
      List[Option[Fragment]](
        Some(fr"update ${Fragment.const0(name)}"),
        NonEmptyList.fromList(params.setters) match {
          case None =>
            sys.error("you must specify a columns to set. use `set` method")
          case Some(setters) =>
            val setFragments = setters.map { setter =>
              val fieldExpr = setter.col(structure.fields)
              val valueExpr = setter.value(structure.fields)
              fr"${fieldExpr.render(ctx)} = ${valueExpr.render(ctx)}${fieldExpr.sqlWriteCast.fold(Fragment.empty)(cast => Fragment.const0(s"::$cast"))}"
            }
            Some(fragments.set(setFragments))
        },
        NonEmptyList.fromList(params.where).map { wheres =>
          fragments.whereAnd(
            wheres.map { where =>
              where(structure.fields).render(ctx)
            }
          )
        },
        if (returning) {
          val colFragments = fragments.comma(
            NonEmptyList.fromListUnsafe(structure.columns).map { col =>
              Fragment.const0(col.sqlReadCast.foldLeft("\"" + col.value(ctx) + "\"") { case (acc, cast) => s"$acc::$cast" })
            }
          )
          Some(fr"returning $colFragments")
        } else None
      ).flatten.intercalate(fr" ")
    }

    override def sql(returning: Boolean): Option[Fragment] = {
      Some(mkSql(returning))
    }

    override def execute: ConnectionIO[Int] =
      mkSql(returning = false).update.run

    override def executeReturnChanged: ConnectionIO[List[Row]] = {
      mkSql(returning = true).query(using read).to[List]
    }
  }
  final case class UpdateBuilderMock[Id, Fields, Row](
      params: UpdateParams[Fields, Row],
      structure: Structure[Fields, Row],
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
        if (params.where.forall(w => structure.untypedEval(w(structure.fields), row))) {
          val newRow = params.setters.foldLeft(row) { case (row, set: UpdateParams.Setter[Fields, nt, Row]) =>
            val field: SqlExpr.FieldLikeNotIdNoHkt[nt, Row] = set.col(structure.fields)
            val newValue: nt = structure.untypedEval(set.value(structure.fields), row)
            field.set(row, newValue)
          }
          map.update(id, newRow)
          changed += newRow
        }
      }
      changed.result()
    }
  }
}
