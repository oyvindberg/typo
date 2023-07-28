package typo.dsl

import anorm.{Row as _, *}
import typo.dsl.Fragment.FragmentStringInterpolator

import java.sql.Connection
import java.util.concurrent.atomic.AtomicInteger

trait UpdateBuilder[Fields[_], Row] {
  protected def params: UpdateParams[Fields, Row]
  protected def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row]

  final def whereStrict(v: Fields[Row] => SqlExpr[Boolean, Required, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.where(v))
  final def where[N[_]: Nullability](v: Fields[Row] => SqlExpr[Boolean, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.where(f => v(f).?.coalesce(false)))

  final def setValue[N[_], T](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: N[T])(implicit T: ToParameterValue[N[T]], P: ParameterMetaData[T]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, _ => SqlExpr.Const[T, N, Row](value, T, P)))

  final def setComputedValue[T, N[_]](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: SqlExpr.FieldLikeNotId[T, N, Row] => SqlExpr[T, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, fields => value(col(fields))))

  final def setComputedValueFromRow[T, N[_]](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: Fields[Row] => SqlExpr[T, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, value))

  def sql(returning: Boolean): Option[Fragment]
  def execute()(implicit c: Connection): Int
  def executeReturnChanged()(implicit c: Connection): List[Row]
}

object UpdateBuilder {
  def apply[Fields[_], Row](name: String, structure: Structure.Relation[Fields, ?, Row], rowParser: Int => RowParser[Row]): UpdateBuilderSql[Fields, Row] =
    UpdateBuilderSql(name, structure, rowParser, UpdateParams.empty)

  case class UpdateBuilderSql[Fields[_], Row](
      name: String,
      structure: Structure.Relation[Fields, ?, Row],
      rowParser: Int => RowParser[Row],
      params: UpdateParams[Fields, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(params: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = params)

    def mkSql(counter: AtomicInteger, returning: Boolean): Fragment = {
      params.setters match {
        case Nil => sys.error("you must specify a columns to set. use `set` method")
        case _   => ()
      }
      List[Iterable[Fragment]](
        Some(frag"update ${Fragment(name)}"),
        params.setters.zipWithIndex.map { case (setter, idx) =>
          val fieldExpr = setter.col(structure.fields)
          val valueExpr = setter.value(structure.fields)
          val cast = fieldExpr.sqlWriteCast.fold(Fragment.empty)(cast => Fragment(s"::$cast"))
          Fragment(if (idx == 0) " set " else ", ") ++ fieldExpr.render(counter) ++ frag" = " ++ valueExpr.render(counter) ++ cast
        },
        params.where
          .map(w => w(structure.fields))
          .reduceLeftOption(_ and _)
          .map { where => Fragment(" where ") ++ where.render(counter) },
        if (returning) {
          val cols = structure.columns
            .map(x => x.sqlReadCast.foldLeft("\"" + x.value + "\"") { case (acc, cast) => s"$acc::$cast" })
            .mkString(",")

          Some(frag" returning ${Fragment(cols)}")
        } else None
      ).flatten.reduce(_ ++ _)
    }

    override def sql(returning: Boolean): Option[Fragment] = {
      Some(mkSql(new AtomicInteger(1), returning))
    }

    override def execute()(implicit c: Connection): Int = {
      val frag = mkSql(new AtomicInteger(0), returning = false)
      SQL(frag.sql).on(frag.params*).executeUpdate()
    }

    override def executeReturnChanged()(implicit c: Connection): List[Row] = {
      val frag = mkSql(new AtomicInteger(0), returning = true)
      SQL(frag.sql).on(frag.params*).as(rowParser(1).*)
    }
  }
  case class UpdateBuilderMock[Id, Fields[_], Row](
      params: UpdateParams[Fields, Row],
      fields: Fields[Row],
      map: scala.collection.mutable.Map[Id, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(params: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = params)

    override def sql(returning: Boolean): Option[Fragment] =
      None

    override def execute()(implicit c: Connection): Int =
      executeReturnChanged().size

    override def executeReturnChanged()(implicit c: Connection): List[Row] = {
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
