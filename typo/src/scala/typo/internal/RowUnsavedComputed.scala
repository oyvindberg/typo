package typo
package internal

object RowUnsavedComputed {
  def apply(relName: db.RelationName, cols: NonEmptyList[ColumnComputed], default: DefaultComputed, naming: Naming): Option[RowUnsavedComputed] = {
    val (defaultCols, restCols) = cols.toList.partition(_.dbCol.columnDefault.nonEmpty)

    NonEmptyList.fromList(defaultCols).map { nonEmpty =>
      val defaultCols = nonEmpty.map { col =>
        (col.copy(tpe = sc.Type.TApply(default.Defaulted, List(col.tpe))), col.tpe)
      }

      new RowUnsavedComputed(
        defaultCols = defaultCols,
        restCols = restCols,
        tpe = sc.Type.Qualified(naming.rowUnsaved(relName))
      )
    }
  }
}

case class RowUnsavedComputed(
    defaultCols: NonEmptyList[(ColumnComputed, sc.Type)],
    restCols: List[ColumnComputed],
    tpe: sc.Type.Qualified
) {
  def allCols: NonEmptyList[ColumnComputed] =
    restCols ::: defaultCols.map { case (col, _) => col }
}
