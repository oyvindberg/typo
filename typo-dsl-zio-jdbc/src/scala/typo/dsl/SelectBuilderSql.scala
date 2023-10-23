package typo.dsl

import zio.jdbc.*
import zio.jdbc.extensions.*
import zio.{Chunk, NonEmptyChunk, ZIO}

import java.util.concurrent.atomic.AtomicInteger

sealed trait SelectBuilderSql[Fields[_], Row] extends SelectBuilder[Fields, Row] {
  def instantiate(counter: AtomicInteger): SelectBuilderSql.Instantiated[Fields, Row]
  def sqlFor(counter: AtomicInteger): Query[Row]

  override def sql: Option[SqlFragment] = Some(sqlFor(new AtomicInteger(0)).sql)

  final override def joinOn[Fields2[_], N[_]: Nullability, Row2](other: SelectBuilder[Fields2, Row2])(
      pred: Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]
  ): SelectBuilder[Joined[Fields, Fields2, *], (Row, Row2)] =
    other match {
      case otherSql: SelectBuilderSql[Fields2, Row2] =>
        new SelectBuilderSql.TableJoin[Fields, Fields2, N, Row, Row2](this, otherSql, pred, SelectParams.empty)
      case _ =>
        sys.error("you cannot mix mock and sql repos")
    }

  final override def leftJoinOn[Fields2[_], N[_]: Nullability, Row2](other: SelectBuilder[Fields2, Row2])(
      pred: Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]
  ): SelectBuilder[LeftJoined[Fields, Fields2, *], (Row, Option[Row2])] =
    other match {
      case otherSql: SelectBuilderSql[Fields2, Row2] =>
        SelectBuilderSql.TableLeftJoin(this, otherSql, pred, SelectParams.empty)
      case _ =>
        sys.error("you cannot mix mock and sql repos")
    }

  final override def toChunk: ZIO[ZConnection, Throwable, Chunk[Row]] =
    sqlFor(new AtomicInteger(0)).selectAll
}

object SelectBuilderSql {
  def apply[Fields[_], OriginalRow, Row](
      name: String,
      structure: Structure.Relation[Fields, OriginalRow, Row],
      read: JdbcDecoder[Row]
  ): SelectBuilderSql[Fields, Row] =
    Relation(name, structure, read, SelectParams.empty)

  private final case class Relation[Fields[_], OriginalRow, Row](
      name: String,
      structure: Structure.Relation[Fields, OriginalRow, Row],
      read: JdbcDecoder[Row],
      params: SelectParams[Fields, Row]
  ) extends SelectBuilderSql[Fields, Row] {
    override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
      copy(params = sqlParams)

    private def sql(counter: AtomicInteger): SqlFragment = {
      val cols = structure.columns
        .map(x => SqlFragment(x.sqlReadCast.foldLeft("\"" + x.value + "\"") { case (acc, cast) => s"$acc::$cast" }))
        .intercalate(SqlFragment(", "))
      val baseSql = sql"select $cols from ${SqlFragment(name)}"
      SelectParams.render(structure.fields, baseSql, counter, params)
    }

    override def sqlFor(counter: AtomicInteger): Query[Row] =
      sql(counter).query[Row](read)

    override def instantiate(counter: AtomicInteger): SelectBuilderSql.Instantiated[Fields, Row] = {
      val completeSql = sql(counter)
      val alias = s"${name.split("\\.").last}${counter.incrementAndGet()}"
      val prefixed = structure.withPrefix(alias)
      val subquery = SelectBuilderSql.InstantiatedPart(alias, NonEmptyChunk.fromIterableOption(prefixed.columns).get, completeSql, joinFrag = SqlFragment.empty)
      SelectBuilderSql.Instantiated(prefixed, NonEmptyChunk.single(subquery), read)
    }
  }

  private final case class TableJoin[Fields1[_], Fields2[_], N[_]: Nullability, Row1, Row2](
      left: SelectBuilderSql[Fields1, Row1],
      right: SelectBuilderSql[Fields2, Row2],
      pred: Joined[Fields1, Fields2, (Row1, Row2)] => SqlExpr[Boolean, N, (Row1, Row2)],
      params: SelectParams[Joined[Fields1, Fields2, *], (Row1, Row2)]
  ) extends SelectBuilderSql[Joined[Fields1, Fields2, *], (Row1, Row2)] {

    override def withParams(sqlParams: SelectParams[Joined[Fields1, Fields2, *], (Row1, Row2)]): SelectBuilder[Joined[Fields1, Fields2, *], (Row1, Row2)] =
      copy(params = sqlParams)

    override def instantiate(counter: AtomicInteger): Instantiated[Joined[Fields1, Fields2, *], (Row1, Row2)] = {
      val leftInstantiated = left.instantiate(counter)
      val rightInstantiated = right.instantiate(counter)
      val newStructure = leftInstantiated.structure.join(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(_.copy(joinFrag = pred(newStructure.fields).render(counter)))

      SelectBuilderSql.Instantiated(
        structure = newStructure,
        parts = leftInstantiated.parts ++ newRightInstantiatedParts,
        decoder = JdbcDecoder.tuple2Decoder(leftInstantiated.decoder, rightInstantiated.decoder)
      )
    }
    override def sqlFor(paramCounter: AtomicInteger): Query[(Row1, Row2)] = {
      val instance = instantiate(paramCounter)
      val combinedFrag = {
        val size = instance.parts.size
        if (size == 1) instance.parts.head.sqlFrag
        else {
          val first = instance.parts.head
          val rest = instance.parts.tail

          val prelude =
            sql"""|select ${instance.columns.map(c => SqlFragment(c.value)).toChunk.intercalate(", ")}
                 |from (
                 |${first.sqlFrag}
                 |) ${SqlFragment(first.alias)}
                 |"""

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag) =>
            sql"""|join (
                 |$sqlFrag
                 |) ${SqlFragment(alias)} on $joinFrag
                 |"""
          }

          prelude ++ joins.reduce(_ ++ _)
        }
      }
      val newCombinedFrag = SelectParams.render[(Row1, Row2), Joined[Fields1, Fields2, *]](instance.structure.fields, combinedFrag, paramCounter, params)

      newCombinedFrag.query(instance.decoder)
    }
  }

  private final case class TableLeftJoin[Fields1[_], Fields2[_], N[_]: Nullability, Row1, Row2](
      left: SelectBuilderSql[Fields1, Row1],
      right: SelectBuilderSql[Fields2, Row2],
      pred: Joined[Fields1, Fields2, (Row1, Row2)] => SqlExpr[Boolean, N, (Row1, Row2)],
      params: SelectParams[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])]
  ) extends SelectBuilderSql[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])] {

    override def withParams(
        sqlParams: SelectParams[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])]
    ): SelectBuilder[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])] =
      copy(params = sqlParams)

    override def instantiate(counter: AtomicInteger): Instantiated[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])] = {
      val leftInstantiated = left.instantiate(counter)
      val rightInstantiated = right.instantiate(counter)
      val newStructure = leftInstantiated.structure.leftJoin(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(_.copy(joinFrag = pred(leftInstantiated.structure.join(rightInstantiated.structure).fields).render(counter)))

      SelectBuilderSql.Instantiated(
        structure = newStructure,
        parts = leftInstantiated.parts ++ newRightInstantiatedParts,
        decoder = JdbcDecoder.tuple2Decoder(leftInstantiated.decoder, JdbcDecoder.optionDecoder(rightInstantiated.decoder))
      )
    }

    override def sqlFor(paramCounter: AtomicInteger): Query[(Row1, Option[Row2])] = {
      val instance = instantiate(paramCounter)
      val combinedFrag = {
        val size = instance.parts.size
        if (size == 1) instance.parts.head.sqlFrag
        else {
          val first = instance.parts.head
          val rest = instance.parts.tail

          val prelude =
            sql"""|select ${instance.columns.map(c => SqlFragment(c.value)).toChunk.intercalate(", ")}
                  |from (
                  |  ${first.sqlFrag}
                  |) ${SqlFragment(first.alias)}
                  |"""

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag) =>
            sql"""|left join (
                  |${sqlFrag}
                  |) ${SqlFragment(alias)} on $joinFrag
                  |"""
          }
          prelude ++ joins.reduce(_ ++ _)
        }
      }
      val newCombinedFrag =
        SelectParams.render[(Row1, Option[Row2]), LeftJoined[Fields1, Fields2, *]](instance.structure.fields, combinedFrag, paramCounter, params)

      newCombinedFrag.query(instance.decoder)
    }
  }

  implicit class ListMapLastOps[T](private val ts: NonEmptyChunk[T]) extends AnyVal {
    def mapLast(f: T => T): NonEmptyChunk[T] = NonEmptyChunk.fromChunk(ts.updated(ts.length - 1, f(ts.last))).get // unsafe
  }

  /** Need this intermediate data structure to generate aliases for tables (and prefixes for column selections) when we have a tree of joined tables. Need to start from the root after the user has
    * constructed the tree
    */
  final case class Instantiated[Fields[_], Row](
      structure: Structure[Fields, Row],
      parts: NonEmptyChunk[SelectBuilderSql.InstantiatedPart],
      decoder: JdbcDecoder[Row]
  ) {
    val columns: NonEmptyChunk[SqlExpr.FieldLikeNoHkt[?, ?]] = parts.flatMap(_.columns)
  }

  /** This is needlessly awkward because the we start with a tree, but we need to make it linear to render it */
  final case class InstantiatedPart(
      alias: String,
      columns: NonEmptyChunk[SqlExpr.FieldLikeNoHkt[?, ?]],
      sqlFrag: SqlFragment,
      joinFrag: SqlFragment
  )
}
