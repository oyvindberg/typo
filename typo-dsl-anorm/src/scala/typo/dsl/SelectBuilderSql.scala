package typo.dsl

import anorm.{AnormException, RowParser, SQL, SimpleSql}
import typo.dsl.Fragment.FragmentStringInterpolator

import java.sql.Connection
import java.util.concurrent.atomic.AtomicInteger

sealed trait SelectBuilderSql[Fields, Row] extends SelectBuilder[Fields, Row] {
  def withPath(path: Path): SelectBuilderSql[Fields, Row]
  def instantiate(ctx: RenderCtx, counter: AtomicInteger): SelectBuilderSql.Instantiated[Fields, Row]
  def sqlFor(ctx: RenderCtx, counter: AtomicInteger): (Fragment, RowParser[Row])

  override lazy val renderCtx: RenderCtx = RenderCtx.from(this)
  override lazy val sql: Option[Fragment] = Some(sqlFor(renderCtx, new AtomicInteger(0))._1)

  final override def joinOn[Fields2, N[_]: Nullability, Row2](
      other: SelectBuilder[Fields2, Row2]
  )(pred: Joined[Fields, Fields2] => SqlExpr[Boolean, N]): SelectBuilder[Joined[Fields, Fields2], (Row, Row2)] =
    other match {
      case otherSql: SelectBuilderSql[Fields2, Row2] =>
        new SelectBuilderSql.TableJoin[Fields, Fields2, N, Row, Row2](this.withPath(Path.LeftInJoin), otherSql.withPath(Path.RightInJoin), pred, SelectParams.empty)
      case _ => sys.error("you cannot mix mock and sql repos")
    }

  final override def leftJoinOn[Fields2, N[_]: Nullability, Row2](other: SelectBuilder[Fields2, Row2])(
      pred: Joined[Fields, Fields2] => SqlExpr[Boolean, N]
  ): SelectBuilder[LeftJoined[Fields, Fields2], (Row, Option[Row2])] =
    other match {
      case otherSql: SelectBuilderSql[Fields2, Row2] =>
        SelectBuilderSql.TableLeftJoin(this.withPath(Path.LeftInJoin), otherSql.withPath(Path.RightInJoin), pred, SelectParams.empty)
      case _ => sys.error("you cannot mix mock and sql repos")
    }

  final override def toList(implicit c: Connection): List[Row] = {
    val (frag, rowParser) = sqlFor(renderCtx, new AtomicInteger(0))
    SimpleSql(SQL(frag.sql), frag.params.map(_.tupled).toMap, RowParser.successful).as(rowParser.*)(c)
  }
}

object SelectBuilderSql {
  def apply[Fields, Row](
      name: String,
      structure: Structure.Relation[Fields, Row],
      rowParser: Int => RowParser[Row]
  ): SelectBuilderSql[Fields, Row] =
    Relation(name, structure, rowParser, SelectParams.empty)

  final case class Relation[Fields, Row](
      name: String,
      structure: Structure.Relation[Fields, Row],
      rowParser: Int => RowParser[Row],
      params: SelectParams[Fields, Row]
  ) extends SelectBuilderSql[Fields, Row] {
    override def withPath(path: Path): SelectBuilderSql[Fields, Row] =
      copy(structure = structure.withPath(path))

    override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
      copy(params = sqlParams)

    private def sql(ctx: RenderCtx, counter: AtomicInteger): Fragment = {
      val cols = structure.columns
        .map(x => x.sqlReadCast.foldLeft(s"\"${x.name}\"") { case (acc, cast) => s"$acc::$cast" })
        .mkString(",")
      val alias = ctx.alias(structure._path)
      val baseSql = frag"select ${Fragment(cols)} from ${Fragment(name)} ${Fragment(alias)}"
      SelectParams.render(structure.fields, baseSql, ctx, counter, params)
    }

    override def sqlFor(ctx: RenderCtx, counter: AtomicInteger): (Fragment, RowParser[Row]) =
      (sql(ctx, counter), rowParser(1))

    override def instantiate(ctx: RenderCtx, counter: AtomicInteger): SelectBuilderSql.Instantiated[Fields, Row] = {
      val part = SelectBuilderSql.InstantiatedPart(
        alias = ctx.alias(structure._path),
        columns = structure.columns,
        sqlFrag = sql(ctx, counter),
        joinFrag = Fragment.empty,
        joinType = JoinType.Inner
      )
      SelectBuilderSql.Instantiated(structure, List(part), rowParser = rowParser)
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

    override def instantiate(ctx: RenderCtx, counter: AtomicInteger): Instantiated[Joined[Fields1, Fields2], (Row1, Row2)] = {

      val leftInstantiated: Instantiated[Fields1, Row1] = left.instantiate(ctx, counter)
      val rightInstantiated: Instantiated[Fields2, Row2] = right.instantiate(ctx, counter)

      val newStructure = leftInstantiated.structure.join(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(
          _.copy(
            joinFrag = pred(newStructure.fields).render(ctx, counter),
            joinType = JoinType.Inner
          )
        )

      SelectBuilderSql.Instantiated(
        structure = newStructure,
        parts = leftInstantiated.parts ++ newRightInstantiatedParts,
        rowParser = (i: Int) =>
          for {
            r1 <- leftInstantiated.rowParser(i)
            r2 <- rightInstantiated.rowParser(i + leftInstantiated.columns.size)
          } yield (r1, r2)
      )
    }

    override def sqlFor(ctx: RenderCtx, counter: AtomicInteger): (Fragment, RowParser[(Row1, Row2)]) = {
      val instance = instantiate(ctx, counter)
      val combinedFrag = instance.parts match {
        case Nil       => sys.error("unreachable (tm)")
        case List(one) => one.sqlFrag
        case first :: rest =>
          val prelude =
            frag"""|select ${instance.columns.map(c => c.render(ctx, counter)).mkFragment(", ")}
                   |from (
                   |${first.sqlFrag.indent(2)}
                   |) ${Fragment(first.alias)}
                   |""".stripMargin

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag, joinType) =>
            frag"""|${joinType.frag} (
                   |${sqlFrag.indent(2)}
                   |) ${Fragment(alias)} on $joinFrag
                   |""".stripMargin
          }

          prelude ++ joins.mkFragment("")
      }
      val newCombinedFrag = SelectParams.render[Joined[Fields1, Fields2], (Row1, Row2)](instance.structure.fields, combinedFrag, ctx, counter, params)

      (newCombinedFrag, instance.rowParser(1))
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

    override def instantiate(ctx: RenderCtx, counter: AtomicInteger): Instantiated[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])] = {
      val leftInstantiated = left.instantiate(ctx, counter)
      val rightInstantiated = right.instantiate(ctx, counter)

      val newStructure = leftInstantiated.structure.leftJoin(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(
          _.copy(
            joinFrag = pred(leftInstantiated.structure.join(rightInstantiated.structure).fields).render(ctx, counter),
            joinType = JoinType.LeftJoin
          )
        )

      SelectBuilderSql.Instantiated(
        newStructure,
        leftInstantiated.parts ++ newRightInstantiatedParts,
        rowParser = (i: Int) =>
          for {
            r1 <- leftInstantiated.rowParser(i)
            /** note, `RowParser` has a `?` combinator, but it doesn't work. fails with exception instead of [[anorm.Error]] */
            r2 <- RowParser[Option[Row2]] { row =>
              try rightInstantiated.rowParser(i + leftInstantiated.columns.size)(row).map(Some.apply)
              catch {
                case x: AnormException if x.message.contains("not found, available columns") => anorm.Success(None)
              }
            }
          } yield (r1, r2)
      )
    }

    override def sqlFor(ctx: RenderCtx, counter: AtomicInteger): (Fragment, RowParser[(Row1, Option[Row2])]) = {
      val instance = instantiate(ctx, counter)
      val combinedFrag = instance.parts match {
        case Nil       => sys.error("unreachable (tm)")
        case List(one) => one.sqlFrag
        case first :: rest =>
          val prelude =
            frag"""|select ${instance.columns.map(c => c.render(ctx, counter)).mkFragment(", ")}
                   |from (
                   |${first.sqlFrag.indent(2)}
                   |) ${Fragment(first.alias)}
                   |""".stripMargin

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag, joinType) =>
            frag"""|${joinType.frag} (
                   |${sqlFrag.indent(2)}
                   |) ${Fragment(alias)} on $joinFrag
                   |""".stripMargin
          }
          prelude ++ joins.mkFragment("")
      }
      val newCombinedFrag =
        SelectParams.render[LeftJoined[Fields1, Fields2], (Row1, Option[Row2])](instance.structure.fields, combinedFrag, ctx, counter, params)

      (newCombinedFrag, instance.rowParser(1))
    }
  }

  implicit class ListMapLastOps[T](private val ts: List[T]) extends AnyVal {
    def mapLast(f: T => T): List[T] = if (ts.isEmpty) ts else ts.updated(ts.length - 1, f(ts.last))
  }

  /** Need this intermediate data structure to generate aliases for tables (and prefixes for column selections) when we have a tree of joined tables. Need to start from the root after the user has
    * constructed the tree
    */
  final case class Instantiated[Fields, Row](
      structure: Structure[Fields, Row],
      parts: List[SelectBuilderSql.InstantiatedPart],
      rowParser: Int => RowParser[Row]
  ) {
    val columns: List[SqlExpr.FieldLikeNoHkt[?, ?]] = parts.flatMap(_.columns)
  }
  sealed abstract class JoinType(_frag: String) {
    val frag = Fragment(_frag)
  }

  object JoinType {
    case object Inner extends JoinType("join")
    case object LeftJoin extends JoinType("left join")
    case object RightJoin extends JoinType("right join")
  }

  /** This is needlessly awkward because the we start with a tree, but we need to make it linear to render it */
  final case class InstantiatedPart(
      alias: String,
      columns: List[SqlExpr.FieldLikeNoHkt[?, ?]],
      sqlFrag: Fragment,
      joinFrag: Fragment,
      joinType: JoinType
  )
}
