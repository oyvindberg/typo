package typo.dsl

import cats.data.NonEmptyList
import cats.syntax.apply.*
import cats.syntax.foldable.*
import doobie.free.connection.ConnectionIO
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.query.Query0
import doobie.util.{Read, fragments}

import java.util.concurrent.atomic.AtomicInteger
import scala.util.Try

sealed trait SelectBuilderSql[Fields[_], Row] extends SelectBuilder[Fields, Row] {
  def instantiate(counter: AtomicInteger): SelectBuilderSql.Instantiated[Fields, Row]
  def sqlFor(counter: AtomicInteger): Query0[Row]

  override def sql: Option[Fragment] = Some(sqlFor(new AtomicInteger(0)).toFragment)

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

  final override def toList: ConnectionIO[List[Row]] =
    sqlFor(new AtomicInteger(0)).to[List]
}

object SelectBuilderSql {
  def apply[Fields[_], OriginalRow, Row](
      name: String,
      structure: Structure.Relation[Fields, OriginalRow, Row],
      read: Read[Row]
  ): SelectBuilderSql[Fields, Row] =
    Relation(name, structure, read, SelectParams.empty)

  private case class Relation[Fields[_], OriginalRow, Row](
      name: String,
      structure: Structure.Relation[Fields, OriginalRow, Row],
      read: Read[Row],
      params: SelectParams[Fields, Row]
  ) extends SelectBuilderSql[Fields, Row] {
    override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
      copy(params = sqlParams)

    private def sql(counter: AtomicInteger): Fragment = {
      val cols = structure.columns
        .map(x => Fragment.const(x.sqlReadCast.foldLeft("\"" + x.value + "\"") { case (acc, cast) => s"$acc::$cast" }))
        .intercalate(Fragment.const(", "))
      val baseSql = fr"select $cols from ${Fragment.const(name)}"
      SelectParams.render(structure.fields, baseSql, counter, params)
    }

    override def sqlFor(counter: AtomicInteger): Query0[Row] =
      sql(counter).query(read)

    override def instantiate(counter: AtomicInteger): SelectBuilderSql.Instantiated[Fields, Row] = {
      val completeSql = sql(counter)
      val alias = s"${name.split("\\.").last}${counter.incrementAndGet()}"
      val prefixed = structure.withPrefix(alias)
      val subquery = SelectBuilderSql.InstantiatedPart(alias, NonEmptyList.fromListUnsafe(prefixed.columns), completeSql, joinFrag = Fragment.empty)
      SelectBuilderSql.Instantiated(prefixed, NonEmptyList.of(subquery), read)
    }
  }

  private case class TableJoin[Fields1[_], Fields2[_], N[_]: Nullability, Row1, Row2](
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
        newStructure,
        leftInstantiated.parts ++ newRightInstantiatedParts.toList,
        read = (leftInstantiated.read, rightInstantiated.read).tupled
      )
    }
    override def sqlFor(paramCounter: AtomicInteger): Query0[(Row1, Row2)] = {
      val instance = instantiate(paramCounter)
      val combinedFrag = instance.parts match {
        case NonEmptyList(one, Nil) => one.sqlFrag
        case NonEmptyList(first, rest) =>
          val prelude =
            fr"""|select ${instance.columns.map(c => Fragment.const(c.value)).intercalate(Fragment.const(", "))}
                   |from (
                   |${first.sqlFrag}
                   |) ${Fragment.const(first.alias)}
                   |""".stripMargin

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag) =>
            fr"""|join (
                   |$sqlFrag
                   |) ${Fragment.const(alias)} on $joinFrag
                   |""".stripMargin
          }
          prelude ++ joins.reduce(_ ++ _)
      }
      val newCombinedFrag = SelectParams.render[(Row1, Row2), Joined[Fields1, Fields2, *]](instance.structure.fields, combinedFrag, paramCounter, params)

      newCombinedFrag.query(instance.read)
    }
  }

  private case class TableLeftJoin[Fields1[_], Fields2[_], N[_]: Nullability, Row1, Row2](
      left: SelectBuilderSql[Fields1, Row1],
      right: SelectBuilderSql[Fields2, Row2],
      pred: Joined[Fields1, Fields2, (Row1, Row2)] => SqlExpr[Boolean, N, (Row1, Row2)],
      params: SelectParams[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])]
  ) extends SelectBuilderSql[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])] {

    override def withParams(
        sqlParams: SelectParams[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])]
    ): SelectBuilder[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])] =
      copy(params = sqlParams)

    def opt[A](read: Read[A]): Read[Option[A]] = {
      new Read[Option[A]](
        read.gets.map { case (get, _) => (get, doobie.enumerated.Nullability.Nullable) },
        (rs, i) => Try(read.unsafeGet(rs, i)).toOption
      )
    }

    override def instantiate(counter: AtomicInteger): Instantiated[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])] = {
      val leftInstantiated = left.instantiate(counter)
      val rightInstantiated = right.instantiate(counter)
      val newStructure = leftInstantiated.structure.leftJoin(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(_.copy(joinFrag = pred(leftInstantiated.structure.join(rightInstantiated.structure).fields).render(counter)))

      SelectBuilderSql.Instantiated(
        newStructure,
        leftInstantiated.parts ++ newRightInstantiatedParts.toList,
        read = (leftInstantiated.read, opt(rightInstantiated.read)).tupled
      )
    }

    override def sqlFor(paramCounter: AtomicInteger): Query0[(Row1, Option[Row2])] = {
      val instance = instantiate(paramCounter)
      val combinedFrag = instance.parts match {
        case NonEmptyList(one, Nil) => one.sqlFrag
        case NonEmptyList(first, rest) =>
          val prelude =
            fr"""|select ${fragments.comma(instance.columns.map(c => Fragment.const(c.value)))}
                   |from (
                   |  ${first.sqlFrag}
                   |) ${Fragment.const(first.alias)}
                   |""".stripMargin

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag) =>
            fr"""|left join (
                   |${sqlFrag}
                   |) ${Fragment.const(alias)} on $joinFrag
                   |""".stripMargin
          }
          prelude ++ joins.reduce(_ ++ _)
      }
      val newCombinedFrag =
        SelectParams.render[(Row1, Option[Row2]), LeftJoined[Fields1, Fields2, *]](instance.structure.fields, combinedFrag, paramCounter, params)

      newCombinedFrag.query(instance.read)
    }
  }

  implicit class ListMapLastOps[T](private val ts: NonEmptyList[T]) extends AnyVal {
    def mapLast(f: T => T): NonEmptyList[T] = NonEmptyList.fromListUnsafe(ts.toList.updated(ts.length - 1, f(ts.last)))
  }

  /** Need this intermediate data structure to generate aliases for tables (and prefixes for column selections) when we have a tree of joined tables. Need to start from the root after the user has
    * constructed the tree
    */
  case class Instantiated[Fields[_], Row](
      structure: Structure[Fields, Row],
      parts: NonEmptyList[SelectBuilderSql.InstantiatedPart],
      read: Read[Row]
  ) {
    val columns: NonEmptyList[SqlExpr.FieldLikeNoHkt[?, ?]] = parts.flatMap(_.columns)
  }

  /** This is needlessly awkward because the we start with a tree, but we need to make it linear to render it */
  case class InstantiatedPart(
      alias: String,
      columns: NonEmptyList[SqlExpr.FieldLikeNoHkt[?, ?]],
      sqlFrag: Fragment,
      joinFrag: Fragment
  )
}
