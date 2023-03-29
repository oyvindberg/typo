package typo
package codegen

case class SqlScriptFiles(script: SqlScriptComputed, naming: Naming, options: Options) {
  val relation = RelationFiles(naming, script.relation, options)
  val all: List[sc.File] = List(
    relation.RowFile,
    relation.RepoTraitFile(script.repoMethods),
    relation.RepoImplFile(script.repoMethods)
  )
}
