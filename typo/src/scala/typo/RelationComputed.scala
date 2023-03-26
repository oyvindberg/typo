package typo

case class RelationComputed(pkg: sc.QIdent, relationName: db.RelationName, cols: Seq[ColumnComputed], maybeId: Option[IdComputed]) {
  val RepoName: sc.QIdent = names.titleCase(pkg, relationName, "Repo")
  val RepoImplName: sc.QIdent = names.titleCase(pkg, relationName, "RepoImpl")
  val RowName: sc.QIdent = names.titleCase(pkg, relationName, "Row")
  val FieldValueName: sc.QIdent = names.titleCase(pkg, relationName, "FieldValue")
}
