package typo
package internal

object ComputedRowUnsaved {
  def apply(source: Source, cols: NonEmptyList[ComputedColumn], default: ComputedDefault, naming: Naming): Option[ComputedRowUnsaved] = {
    val (defaultCols, restCols) = cols.toList.partition(_.dbCol.columnDefault.nonEmpty)

    NonEmptyList.fromList(defaultCols).map { nonEmpty =>
      val defaultCols = nonEmpty.map { col =>
        (col.copy(tpe = sc.Type.TApply(default.Defaulted, List(col.tpe))), col.tpe)
      }

      new ComputedRowUnsaved(
        defaultCols = defaultCols,
        restCols = restCols,
        tpe = sc.Type.Qualified(naming.rowUnsaved(source))
      )
    }
  }
}

case class ComputedRowUnsaved(
    defaultCols: NonEmptyList[(ComputedColumn, sc.Type)],
    restCols: List[ComputedColumn],
    tpe: sc.Type.Qualified
) {
  def allCols: NonEmptyList[ComputedColumn] =
    restCols ::: defaultCols.map { case (col, _) => col }
}
