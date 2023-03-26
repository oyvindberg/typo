package typo
package codegen

case class ViewFiles(view: ViewComputed, options: Options) {
  val relation = RelationFiles(view.relation, options)
  val all: List[sc.File] = List(
    relation.RowFile,
    relation.RepoTraitFile(view.repoMethods),
    relation.RepoImplFile(view.repoMethods),
    relation.FieldValueFile
  )
}
