package typo
package internal
package codegen

import typo.internal.codegen.DbLib.RowType

case class FilesSqlFile(script: ComputedSqlFile, naming: Naming, options: InternalOptions) {
  val relation = FilesRelation(naming, script.names, script.maybeCols.toOption, options)
  val all: List[sc.File] = List(
    relation.RowFile(RowType.Readable, comment = None),
    relation.FieldsFile,
    for {
      dbLib <- options.dbLib
    } yield relation.RepoTraitFile(dbLib, script.repoMethods),
    for {
      dbLib <- options.dbLib
    } yield relation.RepoImplFile(dbLib, script.repoMethods)
  ).flatten
}
