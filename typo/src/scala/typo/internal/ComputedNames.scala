package typo
package internal

case class ComputedNames(naming: Naming, source: Source, maybeId: Option[IdComputed], enableFieldValue: Boolean, enableDsl: Boolean) {
  val RepoName: sc.Type.Qualified = sc.Type.Qualified(naming.repoName(source))
  val RepoImplName: sc.Type.Qualified = sc.Type.Qualified(naming.repoImplName(source))
  val RepoMockName: sc.Type.Qualified = sc.Type.Qualified(naming.repoMockName(source))
  val RowName: sc.Type.Qualified = sc.Type.Qualified(naming.rowName(source))
  val FieldValueName: Option[sc.Type.Qualified] = if (enableFieldValue) Some(sc.Type.Qualified(naming.fieldValueName(source))) else None
  val FieldOrIdValueName: Option[sc.Type.Qualified] = if (enableFieldValue) Some(sc.Type.Qualified(naming.fieldOrIdValueName(source))) else None
  val FieldsName: Option[sc.Type.Qualified] = if (enableDsl) Some(sc.Type.Qualified(naming.fieldsName(source))) else None
  val StructureName: Option[sc.Type.Qualified] = if (enableDsl) Some(sc.Type.Qualified(naming.structureName(source))) else None

  def isIdColumn(colName: db.ColName): Boolean =
    maybeId.exists(_.cols.exists(_.dbName == colName))
}
