package typo
package internal

case class ComputedRowUnsaved(
    categorizedColumns: NonEmptyList[ComputedRowUnsaved.CategorizedColumn],
    categorizedColumnsOriginalOrder: NonEmptyList[ComputedRowUnsaved.CategorizedColumn],
    defaultCols: NonEmptyList[ComputedRowUnsaved.DefaultedCol],
    tpe: sc.Type.Qualified
) {
  def allCols: NonEmptyList[ComputedColumn] = categorizedColumns.map(_.col)
  def normalColumns: List[ComputedColumn] =
    categorizedColumns.toList.collect { case n: ComputedRowUnsaved.NormalCol => n.col }
  def alwaysGeneratedCols: List[ComputedColumn] =
    categorizedColumns.toList.collect { case n: ComputedRowUnsaved.AlwaysGeneratedCol => n.col }
}

object ComputedRowUnsaved {
  sealed trait CategorizedColumn {
    def col: ComputedColumn
  }
  case class DefaultedCol(col: ComputedColumn, originalType: sc.Type) extends CategorizedColumn
  case class AlwaysGeneratedCol(col: ComputedColumn) extends CategorizedColumn
  case class NormalCol(col: ComputedColumn) extends CategorizedColumn

  def apply(source: Source, cols: NonEmptyList[ComputedColumn], default: ComputedDefault, naming: Naming): Option[ComputedRowUnsaved] = {
    val categorizedColumns: NonEmptyList[CategorizedColumn] =
      cols.map {
        case col if col.dbCol.identity.exists(_.ALWAYS) => AlwaysGeneratedCol(col)
        case col if col.dbCol.isDefaulted =>
          DefaultedCol(
            col = col.copy(tpe = sc.Type.TApply(default.Defaulted, List(col.tpe))),
            originalType = col.tpe
          )
        case col => NormalCol(col)
      }
    val (normal, rest) = categorizedColumns.toList.partition {
      case x: NormalCol => true
      case _            => false
    }

    NonEmptyList.fromList(rest.collect { case d: DefaultedCol => d }).map { defaulted =>
      new ComputedRowUnsaved(
        categorizedColumns = NonEmptyList.fromList(normal ++ rest).get,
        categorizedColumnsOriginalOrder = categorizedColumns,
        defaultCols = defaulted,
        tpe = sc.Type.Qualified(naming.rowUnsaved(source))
      )
    }
  }
}
