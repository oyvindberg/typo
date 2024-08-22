package typo
package internal
package codegen

import typo.internal.codegen.DbLib.RowType

case class FilesSqlFile(script: ComputedSqlFile, naming: Naming, options: InternalOptions) {
  val relation = FilesRelation(naming, script.names, script.maybeCols.toOption, None, options, fks = Nil)
  val all: List[sc.File] = List[Iterable[sc.File]](
    relation.RowFile(RowType.Readable, comment = None, maybeUnsavedRow = None),
    relation.FieldsFile,
    if (options.concreteRepo)
      for {
        dbLib <- options.dbLib
      } yield relation.ConcreteRepoImplFile(dbLib, script.repoMethods)
    else
      List(
        for {
          dbLib <- options.dbLib
        } yield relation.RepoTraitFile(dbLib, script.repoMethods),
        for {
          dbLib <- options.dbLib
        } yield relation.RepoImplFile(dbLib, script.repoMethods)
      ).flatten
  ).flatten
}
