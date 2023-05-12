package typo
package internal

case class ComputedNames(naming: Naming, source: Source, cols: NonEmptyList[ComputedColumn], maybeId: Option[IdComputed]) {
  val RepoName: sc.Type.Qualified = sc.Type.Qualified(naming.repoName(source))
  val RepoImplName: sc.Type.Qualified = sc.Type.Qualified(naming.repoImplName(source))
  val RepoMockName: sc.Type.Qualified = sc.Type.Qualified(naming.repoMockName(source))
  val RowName: sc.Type.Qualified = sc.Type.Qualified(naming.rowName(source))
  val FieldValueName: sc.Type.Qualified = sc.Type.Qualified(naming.fieldValueName(source))
  val FieldOrIdValueName: sc.Type.Qualified = sc.Type.Qualified(naming.fieldOrIdValueName(source))

  def isIdColumn(colName: db.ColName): Boolean =
    maybeId.exists(_.cols.exists(_.dbName == colName))
}
