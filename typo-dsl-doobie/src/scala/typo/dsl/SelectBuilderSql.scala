package typo.dsl

import cats.data.NonEmptyList
import cats.syntax.apply.*
import cats.syntax.foldable.*
import doobie.free.connection.ConnectionIO
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.query.Query0
import doobie.util.{Read, fragments}

import scala.util.Try

sealed trait SelectBuilderSql[Fields, Row] extends SelectBuilder[Fields, Row] {
  def withPath(path: Path): SelectBuilderSql[Fields, Row]
  def instantiate(ctx: RenderCtx): SelectBuilderSql.Instantiated[Fields, Row]
  def sqlFor(ctx: RenderCtx): Query0[Row]

  override lazy val renderCtx: RenderCtx = RenderCtx.from(this)
  override lazy val sql: Option[Fragment] = Some(sqlFor(renderCtx).toFragment)

  final override def joinOn[Fields2, N[_]: Nullability, Row2](other: SelectBuilder[Fields2, Row2])(pred: Fields ~ Fields2 => SqlExpr[Boolean, N]): SelectBuilder[Fields ~ Fields2, Row ~ Row2] =
    other match {
      case otherSql: SelectBuilderSql[Fields2, Row2] =>
        new SelectBuilderSql.TableJoin[Fields, Fields2, N, Row, Row2](this.withPath(Path.LeftInJoin), otherSql.withPath(Path.RightInJoin), pred, SelectParams.empty)
      case _ => sys.error("you cannot mix mock and sql repos")
    }

  final override def leftJoinOn[Fields2, N[_]: Nullability, Row2](
      other: SelectBuilder[Fields2, Row2]
  )(pred: Fields ~ Fields2 => SqlExpr[Boolean, N]): SelectBuilder[Fields ~ OuterJoined[Fields2], Row ~ Option[Row2]] =
    other match {
      case otherSql: SelectBuilderSql[Fields2, Row2] =>
        SelectBuilderSql.TableLeftJoin(this.withPath(Path.LeftInJoin), otherSql.withPath(Path.RightInJoin), pred, SelectParams.empty)
      case _ => sys.error("you cannot mix mock and sql repos")
    }

  final override def toList: ConnectionIO[List[Row]] =
    sqlFor(renderCtx).to[List]
}

object SelectBuilderSql {
  def apply[Fields, Row](
      name: String,
      structure: Structure.Relation[Fields, Row],
      read: Read[Row]
  ): SelectBuilderSql[Fields, Row] =
    Relation(name, structure, read, SelectParams.empty)

  final case class Relation[Fields, Row](
      name: String,
      structure: Structure.Relation[Fields, Row],
      read: Read[Row],
      params: SelectParams[Fields, Row]
  ) extends SelectBuilderSql[Fields, Row] {
    override def withPath(path: Path): SelectBuilderSql[Fields, Row] =
      copy(structure = structure.withPath(path))

    override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
      copy(params = sqlParams)

    private def sql(ctx: RenderCtx): Fragment = {
      val cols = structure.columns
        .map(x => Fragment.const0(x.sqlReadCast.foldLeft("\"" + x.name + "\"") { case (acc, cast) => s"$acc::$cast" }))
        .intercalate(Fragment.const0(", "))
      val alias = ctx.alias(structure._path)
      val baseSql = fr"select $cols from ${Fragment.const0(name)} ${Fragment.const0(alias)}"
      SelectParams.render(structure.fields, baseSql, ctx, params)
    }

    override def sqlFor(ctx: RenderCtx): Query0[Row] =
      sql(ctx).query(using read)

    override def instantiate(ctx: RenderCtx): SelectBuilderSql.Instantiated[Fields, Row] = {
      val part = SelectBuilderSql.InstantiatedPart(
        alias = ctx.alias(structure._path),
        columns = NonEmptyList.fromListUnsafe(structure.columns),
        sqlFrag = sql(ctx),
        joinFrag = Fragment.empty,
        joinType = JoinType.Inner
      )
      SelectBuilderSql.Instantiated(structure, NonEmptyList.of(part), read)
    }
  }

  final case class TableJoin[Fields1, Fields2, N[_]: Nullability, Row1, Row2](
      left: SelectBuilderSql[Fields1, Row1],
      right: SelectBuilderSql[Fields2, Row2],
      pred: Fields1 ~ Fields2 => SqlExpr[Boolean, N],
      params: SelectParams[Fields1 ~ Fields2, Row1 ~ Row2]
  ) extends SelectBuilderSql[Fields1 ~ Fields2, Row1 ~ Row2] {
    override lazy val structure: Structure[(Fields1, Fields2), Row1 ~ Row2] =
      left.structure.join(right.structure)

    override def withPath(path: Path): SelectBuilderSql[Fields1 ~ Fields2, Row1 ~ Row2] =
      copy(left = left.withPath(path), right = right.withPath(path))

    override def withParams(sqlParams: SelectParams[Fields1 ~ Fields2, Row1 ~ Row2]): SelectBuilder[Fields1 ~ Fields2, Row1 ~ Row2] =
      copy(params = sqlParams)

    override def instantiate(ctx: RenderCtx): Instantiated[Fields1 ~ Fields2, Row1 ~ Row2] = {
      val leftInstantiated: Instantiated[Fields1, Row1] = left.instantiate(ctx)
      val rightInstantiated: Instantiated[Fields2, Row2] = right.instantiate(ctx)

      val newStructure = leftInstantiated.structure.join(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(_.copy(joinFrag = pred(newStructure.fields).render(ctx), joinType = JoinType.Inner))

      SelectBuilderSql.Instantiated(
        structure = newStructure,
        parts = leftInstantiated.parts ++ newRightInstantiatedParts.toList,
        read = (leftInstantiated.read, rightInstantiated.read).tupled
      )
    }
    override def sqlFor(ctx: RenderCtx): Query0[Row1 ~ Row2] = {
      val instance = instantiate(ctx)
      val combinedFrag = instance.parts match {
        case NonEmptyList(one, Nil) => one.sqlFrag
        case NonEmptyList(first, rest) =>
          val prelude =
            fr"""|select ${instance.columns.map(c => Fragment.const0(c.value(ctx))).intercalate(Fragment.const0(", "))}
                 |from (
                 |${first.sqlFrag}
                 |) ${Fragment.const0(first.alias)}
                 |""".stripMargin

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag, joinType) =>
            fr"""|${joinType.frag} (
                 |$sqlFrag
                 |) ${Fragment.const0(alias)} on $joinFrag
                 |""".stripMargin
          }

          prelude ++ joins.reduce(_ ++ _)
      }

      val newCombinedFrag = SelectParams.render[Fields1 ~ Fields2, Row1 ~ Row2](instance.structure.fields, combinedFrag, ctx, params)

      newCombinedFrag.query(using instance.read)
    }
  }

  final case class TableLeftJoin[Fields1, Fields2, N[_]: Nullability, Row1, Row2](
      left: SelectBuilderSql[Fields1, Row1],
      right: SelectBuilderSql[Fields2, Row2],
      pred: Fields1 ~ Fields2 => SqlExpr[Boolean, N],
      params: SelectParams[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]]
  ) extends SelectBuilderSql[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]] {
    override lazy val structure: Structure[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]] =
      left.structure.leftJoin(right.structure)

    override def withPath(path: Path): SelectBuilderSql[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]] =
      copy(left = left.withPath(path), right = right.withPath(path))

    override def withParams(
        sqlParams: SelectParams[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]]
    ): SelectBuilder[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]] =
      copy(params = sqlParams)

    def opt[A](read: Read[A]): Read[Option[A]] = {
      new Read[Option[A]](
        read.gets.map { case (get, _) => (get, doobie.enumerated.Nullability.Nullable) },
        (rs, i) => Try(read.unsafeGet(rs, i)).toOption
      )
    }

    override def instantiate(ctx: RenderCtx): Instantiated[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]] = {
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
        newStructure,
        leftInstantiated.parts ++ newRightInstantiatedParts.toList,
        read = (leftInstantiated.read, opt(rightInstantiated.read)).tupled
      )
    }

    override def sqlFor(ctx: RenderCtx): Query0[Row1 ~ Option[Row2]] = {
      val instance = instantiate(ctx)
      val combinedFrag = instance.parts match {
        case NonEmptyList(one, Nil) => one.sqlFrag
        case NonEmptyList(first, rest) =>
          val prelude =
            fr"""|select ${fragments.comma(instance.columns.map(c => Fragment.const0(c.value(ctx))))}
                 |from (
                 |  ${first.sqlFrag}
                 |) ${Fragment.const0(first.alias)}
                 |""".stripMargin

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag, joinType) =>
            fr"""|${joinType.frag} (
                 |${sqlFrag}
                 |) ${Fragment.const0(alias)} on $joinFrag
                 |""".stripMargin
          }
          prelude ++ joins.reduce(_ ++ _)
      }
      val newCombinedFrag =
        SelectParams.render[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]](instance.structure.fields, combinedFrag, ctx, params)

      newCombinedFrag.query(using instance.read)
    }
  }

  implicit class ListMapLastOps[T](private val ts: NonEmptyList[T]) extends AnyVal {
    def mapLast(f: T => T): NonEmptyList[T] = NonEmptyList.fromListUnsafe(ts.toList.updated(ts.length - 1, f(ts.last)))
  }

  /** Need this intermediate data structure to generate aliases for tables (and prefixes for column selections) when we have a tree of joined tables. Need to start from the root after the user has
    * constructed the tree
    */
  final case class Instantiated[Fields, Row](
      structure: Structure[Fields, Row],
      parts: NonEmptyList[SelectBuilderSql.InstantiatedPart],
      read: Read[Row]
  ) {
    val columns: NonEmptyList[SqlExpr.FieldLikeNoHkt[?, ?]] = parts.flatMap(_.columns)
  }

  sealed abstract class JoinType(_frag: String) {
    val frag = Fragment.const0(_frag)
  }
  object JoinType {
    case object Inner extends JoinType("join")
    case object LeftJoin extends JoinType("left join")
    case object RightJoin extends JoinType("right join")
  }

  /** This is needlessly awkward because the we start with a tree, but we need to make it linear to render it */
  final case class InstantiatedPart(
      alias: String,
      columns: NonEmptyList[SqlExpr.FieldLikeNoHkt[?, ?]],
      sqlFrag: Fragment,
      joinFrag: Fragment,
      joinType: JoinType
  )
}
