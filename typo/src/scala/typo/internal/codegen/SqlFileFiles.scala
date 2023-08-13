package typo
package internal
package codegen

case class SqlFileFiles(script: ComputedSqlFile, naming: Naming, options: InternalOptions) {
  val relation = RelationFiles(naming, script.names, script.maybeCols.toOption, options)
  val all: List[sc.File] = List(
    relation.RowFile,
    relation.FieldsFile,
    relation.StructureFile,
    for {
      dbLib <- options.dbLib
    } yield relation.RepoTraitFile(dbLib, script.repoMethods),
    for {
      dbLib <- options.dbLib
    } yield relation.RepoImplFile(dbLib, script.repoMethods)
  ).flatten
}
