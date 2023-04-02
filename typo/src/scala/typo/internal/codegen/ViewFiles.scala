package typo
package internal
package codegen

case class ViewFiles(view: ViewComputed, options: InternalOptions) {
  val relation = RelationFiles(view.naming, view.relation, options)
  val all: List[sc.File] = List(
    relation.RowFile,
    relation.RepoTraitFile(view.repoMethods),
    relation.RepoImplFile(view.repoMethods),
    relation.FieldValueFile
  )
}
