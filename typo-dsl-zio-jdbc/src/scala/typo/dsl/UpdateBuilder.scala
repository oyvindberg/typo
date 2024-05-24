package typo.dsl

import zio.jdbc.*
import zio.{Chunk, NonEmptyChunk, ZIO}

trait UpdateBuilder[Fields, Row] {
  protected def params: UpdateParams[Fields, Row]
  protected def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row]

  final def whereStrict(v: Fields => SqlExpr[Boolean, Required]): UpdateBuilder[Fields, Row] =
    withParams(params.where(v))

  final def where[N[_]: Nullability](v: Fields => SqlExpr[Boolean, N]): UpdateBuilder[Fields, Row] =
    withParams(params.where(f => v(f).?.coalesce(false)))

  final def setValue[N[_], T](col: Fields => SqlExpr.FieldLikeNotId[T, N, Row])(value: N[T])(implicit E: JdbcEncoder[N[T]], P: ParameterMetaData[T]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, _ => SqlExpr.Const[T, N](value, E, P)))

  final def setComputedValue[T, N[_]](col: Fields => SqlExpr.FieldLikeNotId[T, N, Row])(value: SqlExpr.FieldLikeNotId[T, N, Row] => SqlExpr[T, N]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, fields => value(col(fields))))

  final def setComputedValueFromRow[T, N[_]](col: Fields => SqlExpr.FieldLikeNotId[T, N, Row])(value: Fields => SqlExpr[T, N]): UpdateBuilder[Fields, Row] =
    withParams(params.set(col, value))

  def sql(returning: Boolean): Option[SqlFragment]
  def execute: ZIO[ZConnection, Throwable, Long]
  def executeReturnChanged: ZIO[ZConnection, Throwable, Chunk[Row]]
}

object UpdateBuilder {
  def apply[Fields, Row](name: String, structure: Structure.Relation[Fields, Row], rowParser: JdbcDecoder[Row]): UpdateBuilderSql[Fields, Row] =
    UpdateBuilderSql(name, structure, rowParser, UpdateParams.empty)

  final case class UpdateBuilderSql[Fields, Row](
      name: String,
      structure: Structure.Relation[Fields, Row],
      decoder: JdbcDecoder[Row],
      params: UpdateParams[Fields, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = sqlParams)

    def mkSql(ctx: RenderCtx, returning: Boolean): SqlFragment = {
      Chunk[Option[SqlFragment]](
        Some(SqlFragment.update(name)),
        NonEmptyChunk.fromIterableOption(params.setters) match {
          case None =>
            sys.error("you must specify a columns to set. use `set` method")
          case Some(setters) =>
            val setFragments = setters
              .map { setter =>
                val fieldExpr = setter.col(structure.fields)
                val valueExpr = setter.value(structure.fields)
                sql"${fieldExpr.render(ctx)} = ${valueExpr.render(ctx)}${fieldExpr.sqlWriteCast.fold(SqlFragment.empty)(cast => SqlFragment(s"::$cast"))}"
              }
              .mkFragment(", ")
            Some(sql"SET $setFragments")
        },
        NonEmptyChunk.fromIterableOption(params.where).map { wheres =>
          sql"WHERE ${wheres.toChunk.map { where => where(structure.fields).render(ctx) }.mkFragment(" AND ")} "
        },
        if (returning) {
          val colFragments =
            structure.columns
              .map { col => SqlFragment(col.sqlReadCast.foldLeft("\"" + col.value(ctx) + "\"") { case (acc, cast) => s"$acc::$cast" }) }
              .mkFragment(", ")

          Some(sql"returning $colFragments")
        } else None
      ).flatten.mkFragment(sql" ")
    }

    override def sql(returning: Boolean): Option[SqlFragment] = {
      Some(mkSql(RenderCtx.Empty, returning))
    }

    override def execute: ZIO[ZConnection, Throwable, Long] =
      mkSql(RenderCtx.Empty, returning = false).update

    override def executeReturnChanged: ZIO[ZConnection, Throwable, Chunk[Row]] = {
      mkSql(RenderCtx.Empty, returning = true).query[Row](using decoder).selectAll
    }
  }

  final case class UpdateBuilderMock[Id, Fields, Row](
      params: UpdateParams[Fields, Row],
      structure: Structure[Fields, Row],
      map: scala.collection.mutable.Map[Id, Row]
  ) extends UpdateBuilder[Fields, Row] {
    override def withParams(sqlParams: UpdateParams[Fields, Row]): UpdateBuilder[Fields, Row] =
      copy(params = sqlParams)

    override def sql(returning: Boolean): Option[SqlFragment] = None

    override def execute: ZIO[ZConnection, Throwable, Long] =
      executeReturnChanged.map(_.size.toLong)

    override def executeReturnChanged: ZIO[ZConnection, Throwable, Chunk[Row]] = ZIO.succeed {
      val changed = Chunk.newBuilder[Row]
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
