package typo.dsl

import anorm.{Row as _, *}
import typo.dsl.Fragment.FragmentStringInterpolator

import java.sql.Connection
import java.util.concurrent.atomic.AtomicInteger

sealed trait SelectBuilderSql[Fields[_], Row] extends SelectBuilder[Fields, Row] {
  def instantiate(counter: AtomicInteger): SelectBuilderSql.Instantiated[Fields, Row]
  def sqlFor(counter: AtomicInteger): (Fragment, RowParser[Row])

  override def sql: Option[Fragment] = Some(sqlFor(new AtomicInteger(0))._1)

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

  final override def toList(implicit c: Connection): List[Row] = {
    val (frag, rowParser) = sqlFor(new AtomicInteger(0))
    SQL(frag.sql).on(frag.params*).as(rowParser.*)(c)
  }
}

object SelectBuilderSql {
  def apply[Fields[_], OriginalRow, Row](
      name: String,
      structure: Structure.Relation[Fields, OriginalRow, Row],
      rowParser: Int => RowParser[Row]
  ): SelectBuilderSql[Fields, Row] =
    Relation(name, structure, rowParser, SelectParams.empty)

  private case class Relation[Fields[_], OriginalRow, Row](
      name: String,
      structure: Structure.Relation[Fields, OriginalRow, Row],
      rowParser: Int => RowParser[Row],
      params: SelectParams[Fields, Row]
  ) extends SelectBuilderSql[Fields, Row] {
    override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
      copy(params = sqlParams)

    private def sql(counter: AtomicInteger): Fragment = {
      val cols = structure.columns
        .map(x => x.sqlReadCast.foldLeft("\"" + x.value + "\"") { case (acc, cast) => s"$acc::$cast" })
        .mkString(",")

      val baseSql = frag"select ${Fragment(cols)} from ${Fragment(name)}"
      SelectParams.render(structure.fields, baseSql, counter, params)
    }

    override def sqlFor(counter: AtomicInteger): (Fragment, RowParser[Row]) =
      (sql(counter), rowParser(1))

    override def instantiate(counter: AtomicInteger): SelectBuilderSql.Instantiated[Fields, Row] = {
      val completeSql = sql(counter)
      val alias = s"${name.split("\\.").last}${counter.incrementAndGet()}"
      val prefixed = structure.withPrefix(alias)
      val subquery = SelectBuilderSql.InstantiatedPart(alias, prefixed.columns, completeSql, joinFrag = Fragment.empty)
      SelectBuilderSql.Instantiated(prefixed, List(subquery), rowParser = rowParser)
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
        leftInstantiated.parts ++ newRightInstantiatedParts,
        rowParser = (i: Int) => {
          val rightRowParser = rightInstantiated.rowParser(i + leftInstantiated.columns.size)
          (leftInstantiated.rowParser(i) ~ rightRowParser).map { case r1 ~ r2 => (r1, r2) }
        }
      )
    }

    override def sqlFor(paramCounter: AtomicInteger): (Fragment, RowParser[(Row1, Row2)]) = {
      val instance = instantiate(paramCounter)
      val combinedFrag = instance.parts match {
        case Nil       => sys.error("unreachable (tm)")
        case List(one) => one.sqlFrag
        case first :: rest =>
          val prelude =
            frag"""|select ${instance.columns.map(c => Fragment(c.value)).mkFragment(", ")}
                   |from (
                   |${first.sqlFrag.indent(2)}
                   |) ${Fragment(first.alias)}
                   |""".stripMargin

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag) =>
            frag"""|join (
                   |${sqlFrag.indent(2)}
                   |) ${Fragment(alias)} on $joinFrag
                   |""".stripMargin
          }
          prelude ++ joins.mkFragment("")
      }
      val newCombinedFrag = SelectParams.render[(Row1, Row2), Joined[Fields1, Fields2, *]](instance.structure.fields, combinedFrag, paramCounter, params)

      (newCombinedFrag, instance.rowParser(1))
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

    override def instantiate(counter: AtomicInteger): Instantiated[LeftJoined[Fields1, Fields2, *], (Row1, Option[Row2])] = {
      val leftInstantiated = left.instantiate(counter)
      val rightInstantiated = right.instantiate(counter)
      val newStructure = leftInstantiated.structure.leftJoin(rightInstantiated.structure)
      val newRightInstantiatedParts = rightInstantiated.parts
        .mapLast(_.copy(joinFrag = pred(leftInstantiated.structure.join(rightInstantiated.structure).fields).render(counter)))

      SelectBuilderSql.Instantiated(
        newStructure,
        leftInstantiated.parts ++ newRightInstantiatedParts,
        rowParser = (i: Int) => {

          /** note, `RowParser` has a `?` combinator, but it doesn't work. fails with exception instead of [[anorm.Error]] */
          val rightOptRowParser = {
            RowParser[Option[Row2]] { row =>
              try rightInstantiated.rowParser(i + leftInstantiated.columns.size)(row).map(Some.apply)
              catch {
                case x: AnormException if x.message.contains("not found, available columns") => anorm.Success(None)
              }
            }
          }

          (leftInstantiated.rowParser(i) ~ rightOptRowParser).map { case r1 ~ r2 => (r1, r2) }
        }
      )
    }

    override def sqlFor(paramCounter: AtomicInteger): (Fragment, RowParser[(Row1, Option[Row2])]) = {
      val instance = instantiate(paramCounter)
      val combinedFrag = instance.parts match {
        case Nil       => sys.error("unreachable (tm)")
        case List(one) => one.sqlFrag
        case first :: rest =>
          val prelude =
            frag"""|select ${instance.columns.map(c => Fragment(c.value)).mkFragment(", ")}
                   |from (
                   |${first.sqlFrag.indent(2)}
                   |) ${Fragment(first.alias)}
                   |""".stripMargin

          val joins = rest.map { case SelectBuilderSql.InstantiatedPart(alias, _, sqlFrag, joinFrag) =>
            frag"""|left join (
                   |${sqlFrag.indent(2)}
                   |) ${Fragment(alias)} on $joinFrag
                   |""".stripMargin
          }
          prelude ++ joins.mkFragment("")
      }
      val newCombinedFrag =
        SelectParams.render[(Row1, Option[Row2]), LeftJoined[Fields1, Fields2, *]](instance.structure.fields, combinedFrag, paramCounter, params)

      (newCombinedFrag, instance.rowParser(1))
    }
  }

  implicit class ListMapLastOps[T](private val ts: List[T]) extends AnyVal {
    def mapLast(f: T => T): List[T] = if (ts.isEmpty) ts else ts.updated(ts.length - 1, f(ts.last))
  }

  /** Need this intermediate data structure to generate aliases for tables (and prefixes for column selections) when we have a tree of joined tables. Need to start from the root after the user has
    * constructed the tree
    */
  case class Instantiated[Fields[_], Row](
      structure: Structure[Fields, Row],
      parts: List[SelectBuilderSql.InstantiatedPart],
      rowParser: Int => RowParser[Row]
  ) {
    val columns: List[SqlExpr.FieldLikeNoHkt[?, ?]] = parts.flatMap(_.columns)
  }

  /** This is needlessly awkward because the we start with a tree, but we need to make it linear to render it */
  case class InstantiatedPart(
      alias: String,
      columns: List[SqlExpr.FieldLikeNoHkt[?, ?]],
      sqlFrag: Fragment,
      joinFrag: Fragment
  )
}
