package typo
package internal
package codegen

import typo.internal.codegen.DbLib.RowType

case class FilesView(view: ComputedView, options: InternalOptions) {
  val relation = FilesRelation(view.naming, view.names, Some(view.cols), None, options, fks = Nil)
  val all: List[sc.File] = List[Iterable[sc.File]](
    relation.RowFile(RowType.Readable, view.view.comment, maybeUnsavedRow = None),
    relation.FieldsFile,
    if (options.concreteRepo)
      for {
        dbLib <- options.dbLib
      } yield relation.ConcreteRepoImplFile(dbLib, view.repoMethods)
    else
      List(
        for {
          dbLib <- options.dbLib
        } yield relation.RepoTraitFile(dbLib, view.repoMethods),
        for {
          dbLib <- options.dbLib
        } yield relation.RepoImplFile(dbLib, view.repoMethods)
      ).flatten,
    relation.FieldValueFile
  ).flatten
}
