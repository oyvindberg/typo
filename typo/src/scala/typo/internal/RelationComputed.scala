package typo
package internal

case class RelationComputed(naming: Naming, relationName: db.RelationName, cols: NonEmptyList[ColumnComputed], maybeId: Option[IdComputed]) {
  val RepoName: sc.Type.Qualified = sc.Type.Qualified(naming.repoName(relationName))
  val RepoImplName: sc.Type.Qualified = sc.Type.Qualified(naming.repoImplName(relationName))
  val RepoMockName: sc.Type.Qualified = sc.Type.Qualified(naming.repoMockName(relationName))
  val RowName: sc.Type.Qualified = sc.Type.Qualified(naming.rowName(relationName))
  val FieldValueName: sc.Type.Qualified = sc.Type.Qualified(naming.fieldValueName(relationName))
  val FieldOrIdValueName: sc.Type.Qualified = sc.Type.Qualified(naming.fieldOrIdValueName(relationName))

  def isIdColumn(colName: db.ColName): Boolean =
    maybeId.exists(_.cols.exists(_.dbName == colName))
}
