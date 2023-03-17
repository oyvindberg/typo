package typo

case class ViewFiles(view: ViewComputed, dbLib: DbLib, jsonLib: JsonLib) {
  val relation = RelationFiles(view.relation, dbLib, jsonLib)
  val all: List[sc.File] = List(
    relation.RowFile,
    relation.RepoTraitFile(view.repoMethods),
    relation.RepoImplTraitFile(view.repoMethods),
    relation.FieldValueFile,
  )
}
