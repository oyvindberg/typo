package typo
package internal

object ComputedRowUnsaved {
  def apply(source: Source, allCols: NonEmptyList[ComputedColumn], default: ComputedDefault, naming: Naming): Option[ComputedRowUnsaved] = {
    val (alwaysGenerated, notAlwaysGenerated) = allCols.toList.partition(c => c.dbCol.maybeGenerated.exists(_.ALWAYS))
    val (defaultCols, restCols) = notAlwaysGenerated.partition(c => c.dbCol.isDefaulted)
    val defaultCols2 = defaultCols.map { col =>
      (col.copy(tpe = sc.Type.TApply(default.Defaulted, List(col.tpe))), col.tpe)
    }
    NonEmptyList.fromList(restCols ::: defaultCols2.map { case (col, _) => col }).collect {
      case unsavedCols if defaultCols.nonEmpty || alwaysGenerated.nonEmpty =>
        new ComputedRowUnsaved(
          allCols,
          defaultCols = defaultCols2,
          restCols = restCols,
          alwaysGeneratedCols = alwaysGenerated,
          unsavedCols = unsavedCols,
          tpe = sc.Type.Qualified(naming.rowUnsaved(source))
        )
    }
  }
}

case class ComputedRowUnsaved(
    allCols: NonEmptyList[ComputedColumn],
    defaultCols: List[(ComputedColumn, sc.Type)],
    restCols: List[ComputedColumn],
    alwaysGeneratedCols: List[ComputedColumn],
    unsavedCols: NonEmptyList[ComputedColumn],
    tpe: sc.Type.Qualified
)
