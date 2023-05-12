package typo
package internal
package codegen

case class SqlFileFiles(script: ComputedSqlFile, naming: Naming, options: InternalOptions) {
  val relation = RelationFiles(naming, script.names, options)
  val all: List[sc.File] = List(
    relation.RowFile,
    relation.RepoTraitFile(script.repoMethods),
    relation.RepoImplFile(script.repoMethods)
  )
}
