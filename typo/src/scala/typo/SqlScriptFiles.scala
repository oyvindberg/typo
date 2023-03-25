package typo

case class SqlScriptFiles(script: SqlScriptComputed, options: Options) {
  val relation = RelationFiles(script.relation, options)
  val all: List[sc.File] = List(
    relation.RowFile,
    relation.RepoTraitFile(script.repoMethods),
    relation.RepoImplFile(script.repoMethods),
    relation.FieldValueFile
  )
}
