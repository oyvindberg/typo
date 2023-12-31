package typo
package internal

object ComputedRowUnsaved {
  def apply(source: Source, cols: NonEmptyList[ComputedColumn], default: ComputedDefault, naming: Naming): Option[ComputedRowUnsaved] = {
    val (alwaysGenerated, notAlwaysGenerated) = cols.toList.partition(c => c.dbCol.identity.exists(_.ALWAYS))
    val (defaultCols, restCols) = notAlwaysGenerated.partition(c => c.dbCol.columnDefault.nonEmpty || c.dbCol.identity.exists(_.`BY DEFAULT`))

    NonEmptyList.fromList(defaultCols).map { nonEmpty =>
      val defaultCols = nonEmpty.map { col =>
        (col.copy(tpe = sc.Type.TApply(default.Defaulted, List(col.tpe))), col.tpe)
      }

      new ComputedRowUnsaved(
        defaultCols = defaultCols,
        restCols = restCols,
        alwaysGeneratedCols = alwaysGenerated,
        tpe = sc.Type.Qualified(naming.rowUnsaved(source))
      )
    }
  }
}

case class ComputedRowUnsaved(
    defaultCols: NonEmptyList[(ComputedColumn, sc.Type)],
    restCols: List[ComputedColumn],
    alwaysGeneratedCols: List[ComputedColumn],
    tpe: sc.Type.Qualified
) {
  def allCols: NonEmptyList[ComputedColumn] =
    restCols ::: defaultCols.map { case (col, _) => col }
}
