package typo.dsl

import scala.math.Ordering.Implicits.seqOrdering

/** Calculates aliases for all unique list of [[Path]]s in a select query. This is used to evaluate expressions from an [[SqlExpr]] when we have joined relations */
class RenderCtx(val alias: Map[List[Path], String]) extends AnyVal

object RenderCtx {
  val Empty = new RenderCtx(Map.empty)

  def from[F, R](s: SelectBuilderSql[F, R]): RenderCtx = {
    def findPathsAndTableNames(s: SelectBuilderSql[?, ?]): List[(List[Path], String)] =
      s match {
        case SelectBuilderSql.Relation(name, structure, _, _)  => List(structure._path -> name)
        case SelectBuilderSql.TableJoin(left, right, _, _)     => findPathsAndTableNames(left) ++ findPathsAndTableNames(right)
        case SelectBuilderSql.TableLeftJoin(left, right, _, _) => findPathsAndTableNames(left) ++ findPathsAndTableNames(right)
      }

    val nameForPathsMap: Map[List[Path], String] =
      findPathsAndTableNames(s)
        .groupMap { case (_, name) => name.split('.').last } { case (path, _) => path }
        .flatMap { case (sameName, paths) =>
          paths.sorted.zipWithIndex.map { case (path, idx) => path -> s"$sameName$idx" }
        }

    new RenderCtx(nameForPathsMap)
  }
}
