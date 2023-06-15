package typo
package internal
package codegen

case class ViewFiles(view: ComputedView, options: InternalOptions) {
  val relation = RelationFiles(view.naming, view.names, options)
  val all: List[sc.File] = List(
    Some(relation.RowFile),
    for {
      dbLib <- options.dbLib
    } yield relation.RepoTraitFile(dbLib, view.repoMethods),
    for {
      dbLib <- options.dbLib
    } yield relation.RepoImplFile(dbLib, view.repoMethods),
    Some(relation.FieldValueFile)
  ).flatten
}
