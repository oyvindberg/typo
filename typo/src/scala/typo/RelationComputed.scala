package typo

case class RelationComputed(naming: Naming, relationName: db.RelationName, cols: Seq[ColumnComputed], maybeId: Option[IdComputed]) {
  val RepoName: sc.QIdent = naming.repoName(relationName)
  val RepoImplName: sc.QIdent = naming.repoImplName(relationName)
  val RowName: sc.QIdent = naming.rowName(relationName)
  val FieldValueName: sc.QIdent = naming.fieldValueName(relationName)
}
