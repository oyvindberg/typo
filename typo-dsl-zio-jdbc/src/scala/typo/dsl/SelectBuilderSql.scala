package typo.dsl

import zio.jdbc.*
import zio.{Chunk, NonEmptyChunk, ZIO}

sealed trait SelectBuilderSql[Fields, Row] extends SelectBuilder[Fields, Row] {
  def withPath(path: Path): SelectBuilderSql[Fields, Row]
  def instantiate(ctx: RenderCtx): SelectBuilderSql.Instantiated[Fields, Row]
  def sqlFor(ctx: RenderCtx): Query[Row]

  override lazy val renderCtx: RenderCtx = RenderCtx.from(this)
  override def sql: Option[SqlFragment] = Some(sqlFor(renderCtx).sql)

  final override def joinOn[Fields2, N[_]: Nullability, Row2](
      other: SelectBuilder[Fields2, Row2]
  )(pred: Joined[Fields, Fields2] => SqlExpr[Boolean, N]): SelectBuilder[Joined[Fields, Fields2], (Row, Row2)] =
    other match {
      case otherSql: SelectBuilderSql[Fields2, Row2] =>
        new SelectBuilderSql.TableJoin[Fields, Fields2, N, Row, Row2](this.withPath(Path.LeftInJoin), otherSql.withPath(Path.RightInJoin), pred, SelectParams.empty)
      case _ => sys.error("you cannot mix mock and sql repos")
    }

  final override def leftJoinOn[Fields2, N[_]: Nullability, Row2](
      other: SelectBuilder[Fields2, Row2]
  )(pred: Joined[Fields, Fields2] => SqlExpr[Boolean, N]): SelectBuilder[LeftJoined[Fields, Fields2], (Row, Option[Row2])] =
    other match {
      case otherSql: SelectBuilderSql[Fields2, Row2] =>
        SelectBuilderSql.TableLeftJoin(this.withPath(Path.LeftInJoin), otherSql.withPath(Path.RightInJoin), pred, SelectParams.empty)
      case _ => sys.error("you cannot mix mock and sql repos")
    }

  final override def toChunk: ZIO[ZConnection, Throwable, Chunk[Row]] =
    sqlFor(renderCtx).selectAll
}

object SelectBuilderSql {
  def apply[Fields, Row](
      name: String,
      structure: Structure.Relation[Fields, Row],
      read: JdbcDecoder[Row]
  ): SelectBuilderSql[Fields, Row] =
    Relation(name, structure, read, SelectParams.empty)

  final case class Relation[Fields, Row](
      name: String,
      structure: Structure.Relation[Fields, Row],
      read: JdbcDecoder[Row],
      params: SelectParams[Fields, Row]
  ) extends SelectBuilderSql[Fields, Row] {
    override def withPath(path: Path): SelectBuilderSql[Fields, Row] =
      copy(structure = structure.withPath(path))

    override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
      copy(params = sqlParams)

    private def sql(ctx: RenderCtx): SqlFragment = {
      val cols = structure.columns
        .map(x => SqlFragment(x.sqlReadCast.foldLeft("\"" + x.name + "\"") { case (acc, cast) => s"$acc::$cast" }))
        .mkFragment(", ")
      val alias = ctx.alias(structure._path)
      val baseSql = sql"select $cols from ${SqlFragment(name)} ${SqlFragment(alias)}"
      SelectParams.render(structure.fields, baseSql, ctx, params)
    }

    override def sqlFor(ctx: RenderCtx): Query[Row] =
      sql(ctx).query[Row](using read)

    override def instantiate(ctx: RenderCtx): SelectBuilderSql.Instantiated[Fields, Row] = {
      val part = SelectBuilderSql.InstantiatedPart(
        alias = ctx.alias(structure._path),
        columns = NonEmptyChunk.fromIterableOption(structure.columns).get,
        sqlFrag = sql(ctx),
        joinFrag = SqlFragment.empty,
        joinType = JoinType.Inner
      )
      SelectBuilderSql.Instantiated(structure, NonEmptyChunk.single(part), read)
    }
  }

  final case class TableJoin[Fields1, Fields2, N[_]: Nullability, Row1, Row2](
      left: SelectBuilderSql[Fields1, Row1],
      right: SelectBuilderSql[Fields2, Row2],
      pred: Joined[Fields1, Fields2] => SqlExpr[Boolean, N],
      params: SelectParams[Joined[Fields1, Fields2], (Row1, Row2)]
  ) extends SelectBuilderSql[Joined[Fields1, Fields2], (Row1, Row2)] {
    override lazy val structure: Structure[(Fields1, Fields2), (Row1, Row2)] =
      left.structure.join(right.structure)

    override def withPath(path: Path): SelectBuilderSql[Joined[Fields1, Fields2], (Row1, Row2)] =
      copy(left = left.withPath(path), right = right.withPath(path))

    override def withParams(sqlParams: SelectParams[Joined[Fields1, Fields2], (Row1, Row2)]): SelectBuilder[Joined[Fields1, Fields2], (Row1, Row2)] =
      copy(params = sqlParams)

    override def instantiate(ctx: RenderCtx): Instantiated[Joined[Fields1, Fields2], (Row1, Row2)] = {
      val leftInstantiated: Instantiated[Fields1, Row1] = left.instantiate(ctx)
      val rightInstantiated: Instantiated[Fields2, Row2] = right.instantiate(ctx)

      val newStructure = leftInstantiated.structure.join(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(
          _.copy(
            joinFrag = pred(newStructure.fields).render(ctx),
            joinType = JoinType.Inner
          )
        )

      SelectBuilderSql.Instantiated(
        structure = newStructure,
        parts = leftInstantiated.parts ++ newRightInstantiatedParts,
        decoder = JdbcDecoder.tuple2Decoder(leftInstantiated.decoder, rightInstantiated.decoder)
      )
    }
    override def sqlFor(ctx: RenderCtx): Query[(Row1, Row2)] = {
      val instance = instantiate(ctx)
      val combinedFrag = {
        val size = instance.parts.size
        if (size == 1) instance.parts.head.sqlFrag
        else {
          val first = instance.parts.head
          val rest = instance.parts.tail

          val prelude =
            sql"""select ${instance.columns.map(c => c.render(ctx)).mkFragment(", ")}
                 from (
                 ${first.sqlFrag}
                 ) ${SqlFragment(first.alias)}
                 """

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag, joinType) =>
            sql"""${joinType.frag} (
                 $sqlFrag
                 ) ${SqlFragment(alias)} on $joinFrag
                 """
          }

          prelude ++ joins.reduce(_ ++ _)
        }
      }
      val newCombinedFrag = SelectParams.render[Joined[Fields1, Fields2], (Row1, Row2)](instance.structure.fields, combinedFrag, ctx, params)

      newCombinedFrag.query(using instance.decoder)
    }
  }

  final case class TableLeftJoin[Fields1, Fields2, N[_]: Nullability, Row1, Row2](
      left: SelectBuilderSql[Fields1, Row1],
      right: SelectBuilderSql[Fields2, Row2],
      pred: Joined[Fields1, Fields2] => SqlExpr[Boolean, N],
      params: SelectParams[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])]
  ) extends SelectBuilderSql[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])] {
    override lazy val structure: Structure[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])] =
      left.structure.leftJoin(right.structure)

    override def withPath(path: Path): SelectBuilderSql[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])] =
      copy(left = left.withPath(path), right = right.withPath(path))

    override def withParams(
        sqlParams: SelectParams[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])]
    ): SelectBuilder[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])] =
      copy(params = sqlParams)

    override def instantiate(ctx: RenderCtx): Instantiated[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])] = {
      val leftInstantiated = left.instantiate(ctx)
      val rightInstantiated = right.instantiate(ctx)

      val newStructure = leftInstantiated.structure.leftJoin(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(
          _.copy(
            joinFrag = pred(leftInstantiated.structure.join(rightInstantiated.structure).fields).render(ctx),
            joinType = JoinType.LeftJoin
          )
        )

      SelectBuilderSql.Instantiated(
        structure = newStructure,
        parts = leftInstantiated.parts ++ newRightInstantiatedParts,
        decoder = JdbcDecoder.tuple2Decoder(leftInstantiated.decoder, JdbcDecoder.optionDecoder(rightInstantiated.decoder))
      )
    }

    override def sqlFor(ctx: RenderCtx): Query[(Row1, Option[Row2])] = {
      val instance = instantiate(ctx)
      val combinedFrag = {
        val size = instance.parts.size
        if (size == 1) instance.parts.head.sqlFrag
        else {
          val first = instance.parts.head
          val rest = instance.parts.tail

          val prelude =
            sql"""select ${instance.columns.map(c => c.render(ctx)).mkFragment(", ")}
                  from (
                    ${first.sqlFrag}
                  ) ${SqlFragment(first.alias)}
                  """

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag, joinType) =>
            sql"""${joinType.frag} (
                  ${sqlFrag}
                  ) ${SqlFragment(alias)} on $joinFrag
                  """
          }
          prelude ++ joins.reduce(_ ++ _)
        }
      }
      val newCombinedFrag =
        SelectParams.render[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])](instance.structure.fields, combinedFrag, ctx, params)

      newCombinedFrag.query(using instance.decoder)
    }
  }

  implicit class ListMapLastOps[T](private val ts: NonEmptyChunk[T]) extends AnyVal {
    def mapLast(f: T => T): NonEmptyChunk[T] = NonEmptyChunk.fromChunk(ts.updated(ts.length - 1, f(ts.last))).get // unsafe
  }

  /** Need this intermediate data structure to generate aliases for tables (and prefixes for column selections) when we have a tree of joined tables. Need to start from the root after the user has
    * constructed the tree
    */
  final case class Instantiated[Fields, Row](
      structure: Structure[Fields, Row],
      parts: NonEmptyChunk[SelectBuilderSql.InstantiatedPart],
      decoder: JdbcDecoder[Row]
  ) {
    val columns: NonEmptyChunk[SqlExpr.FieldLikeNoHkt[?, ?]] = parts.flatMap(_.columns)
  }
  sealed abstract class JoinType(_frag: String) {
    val frag = SqlFragment(_frag)
  }
  object JoinType {
    case object Inner extends JoinType("join")
    case object LeftJoin extends JoinType("left join")
    case object RightJoin extends JoinType("right join")
  }

  /** This is needlessly awkward because the we start with a tree, but we need to make it linear to render it */
  final case class InstantiatedPart(
      alias: String,
      columns: NonEmptyChunk[SqlExpr.FieldLikeNoHkt[?, ?]],
      sqlFrag: SqlFragment,
      joinFrag: SqlFragment,
      joinType: JoinType
  )
}
