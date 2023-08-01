package typo

class Naming(val pkg: sc.QIdent) {
  protected def fragments(source: Source): (sc.QIdent, String) = {
    def forRelPath(relPath: RelPath): (sc.QIdent, String) =
      (
        pkg / relPath.segments.init.map(sc.Ident.apply),
        relPath.segments.last.replace(".sql", "")
      )

    source match {
      case relation: Source.Relation =>
        (pkg / relation.name.schema.toList.map(sc.Ident.apply), relation.name.name)
      case Source.SqlFile(relPath)      => forRelPath(relPath)
      case Source.SqlFileParam(relPath) => forRelPath(relPath)
    }
  }

  def suffixFor(source: Source): String =
    source match {
      case Source.Table(_)        => ""
      case Source.View(_, false)  => "View"
      case Source.View(_, true)   => "MV"
      case Source.SqlFile(_)      => "Sql"
      case Source.SqlFileParam(_) => ""
    }

  protected def relation(source: Source, suffix: String): sc.QIdent = {
    val (init, name) = fragments(source)
    val suffix0 = suffixFor(source)
    init / sc.Ident(name) / sc.Ident(Naming.titleCase(name)).appended(suffix0 + suffix)
  }

  protected def tpe(name: db.RelationName, suffix: String): sc.QIdent =
    pkg / name.schema.map(sc.Ident.apply).toList / sc.Ident(Naming.titleCase(name.name)).appended(suffix)

  def idName(source: Source): sc.QIdent = relation(source, "Id")
  def repoName(source: Source): sc.QIdent = relation(source, "Repo")
  def repoImplName(source: Source): sc.QIdent = relation(source, "RepoImpl")
  def repoMockName(source: Source): sc.QIdent = relation(source, "RepoMock")
  def rowName(source: Source): sc.QIdent = relation(source, "Row")
  def fieldsName(source: Source): sc.QIdent = relation(source, "Fields")
  def structureName(source: Source): sc.QIdent = relation(source, "Structure")
  def fieldValueName(source: Source): sc.QIdent = relation(source, "FieldValue")
  def fieldOrIdValueName(source: Source): sc.QIdent = relation(source, "FieldOrIdValue")
  def rowUnsaved(source: Source): sc.QIdent = relation(source, "RowUnsaved")

  def className(names: List[sc.Ident]): sc.QIdent = pkg / names

  def enumName(name: db.RelationName): sc.QIdent =
    tpe(name, "")

  def domainName(name: db.RelationName): sc.QIdent =
    tpe(name, "")

  def enumValue(name: String): sc.Ident = sc.Ident(name)

  // field names
  def field(name: db.ColName): sc.Ident =
    Naming.camelCase(name.value.split('_'))

  // multiple field names together into one name
  def field(colNames: NonEmptyList[db.ColName]): sc.Ident =
    Naming.camelCase(colNames.map(field).map(_.value).toArray)
}

object Naming {
  def camelCase(strings: Array[String]): sc.Ident =
    sc.Ident(
      strings.zipWithIndex
        .map {
          case (s, 0) => s
          case (s, _) => s.capitalize
        }
        .mkString("")
    )

  def titleCase(name: String): String =
    name.split('_').map(_.capitalize).mkString("")
}
