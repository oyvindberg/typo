package typo
package internal

case class RelationComputed(naming: Naming, relationName: db.RelationName, cols: NonEmptyList[ColumnComputed], maybeId: Option[IdComputed]) {
  val RepoName: sc.QIdent = naming.repoName(relationName)
  val RepoImplName: sc.QIdent = naming.repoImplName(relationName)
  val RowName: sc.QIdent = naming.rowName(relationName)
  val FieldValueName: sc.QIdent = naming.fieldValueName(relationName)
  val FieldOrIdValueName: sc.QIdent = naming.fieldOrIdValueName(relationName)

  def isIdColumn(colName: db.ColName): Boolean =
    maybeId.exists(_.cols.exists(_.dbName == colName))
}
