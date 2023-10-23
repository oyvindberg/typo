package typo.dsl

import zio.jdbc.*
import zio.jdbc.extensions.*
import zio.{Chunk, NonEmptyChunk, ZIO}

import java.util.concurrent.atomic.AtomicInteger

trait UpdateBuilder[Fields[_], Row] {
  protected def params: UpdateParams[Fields, Row]
  protected def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row]

  final def whereStrict(v: Fields[Row] => SqlExpr[Boolean, Required, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.where(v))

  final def where[N[_]: Nullability](v: Fields[Row] => SqlExpr[Boolean, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.where(f => v(f).?.coalesce(false)))

  final def setValue[N[_], T](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: N[T])(implicit P: JdbcEncoder[N[T]]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, _ => SqlExpr.Const[T, N, Row](value, P)))

  final def setComputedValue[T, N[_]](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: SqlExpr.FieldLikeNotId[T, N, Row] => SqlExpr[T, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, fields => value(col(fields))))

  final def setComputedValueFromRow[T, N[_]](col: Fields[Row] => SqlExpr.FieldLikeNotId[T, N, Row])(value: Fields[Row] => SqlExpr[T, N, Row]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, value))

  def sql(returning: Boolean): Option[SqlFragment]
  def execute: ZIO[ZConnection, Throwable, Long]
  def executeReturnChanged: ZIO[ZConnection, Throwable, Chunk[Row]]
}

object UpdateBuilder {
  def apply[Fields[_], Row](name: String, structure: Structure.Relation[Fields, ?, Row], rowParser: JdbcDecoder[Row]): UpdateBuilderSql[Fields, Row] =
    UpdateBuilderSql(name, structure, rowParser, UpdateParams.empty)

  final case class UpdateBuilderSql[Fields[_], Row](
      name: String,
      structure: Structure.Relation[Fields, ?, Row],
      decoder: JdbcDecoder[Row],
      params: UpdateParams[Fields, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(params: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = params)

    def mkSql(counter: AtomicInteger, returning: Boolean): SqlFragment = {
      List[Option[SqlFragment]](
        Some(SqlFragment.update(name)),
        NonEmptyChunk.fromIterableOption(params.setters) match {
          case None =>
            sys.error("you must specify a columns to set. use `set` method")
          case Some(setters) =>
            val setFragments = setters.map { setter =>
              val fieldExpr = setter.col(structure.fields)
              val valueExpr = setter.value(structure.fields)
              sql"${fieldExpr.render(counter)} = ${valueExpr.render(counter)}${fieldExpr.sqlWriteCast.fold(SqlFragment.empty)(cast => SqlFragment(s"::$cast"))}"
            }
            Some(SqlFragment(setFragments.toChunk.flatMap(_.segments)))
        },
        NonEmptyChunk.fromIterableOption(params.where).map { wheres =>
          SqlFragment.whereAnd(
            wheres.map { where =>
              where(structure.fields).render(counter)
            }
          )
        },
        if (returning) {
          val colFragments =
            NonEmptyChunk
              .fromIterableOption(structure.columns)
              .get // unsafe
              .map { col => SqlFragment(col.sqlReadCast.foldLeft("\"" + col.value + "\"") { case (acc, cast) => s"$acc::$cast" }) }
              .toChunk
              .intercalate(", ")

          Some(sql"returning $colFragments")
        } else None
      ).flatten.intercalate(sql" ")
    }

    override def sql(returning: Boolean): Option[SqlFragment] = {
      Some(mkSql(new AtomicInteger(1), returning))
    }

    override def execute: ZIO[ZConnection, Throwable, Long] =
      mkSql(new AtomicInteger(0), returning = false).update

    override def executeReturnChanged: ZIO[ZConnection, Throwable, Chunk[Row]] = {
      mkSql(new AtomicInteger(0), returning = true).query[Row](decoder).selectAll
    }
  }
  final case class UpdateBuilderMock[Id, Fields[_], Row](
      params: UpdateParams[Fields, Row],
      fields: Fields[Row],
      map: scala.collection.mutable.Map[Id, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(params: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = params)

    override def sql(returning: Boolean): Option[SqlFragment] = None

    override def execute: ZIO[ZConnection, Throwable, Long] =
      executeReturnChanged.map(_.size.toLong)

    override def executeReturnChanged: ZIO[ZConnection, Throwable, Chunk[Row]] = ZIO.succeed {
      val changed = Chunk.newBuilder[Row]
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
