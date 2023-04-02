package typo

class Naming(pkg: sc.QIdent) {
  protected def relation(name: db.RelationName, suffix: String): sc.QIdent =
    pkg / name.schema.map(sc.Ident.apply).toList / sc.Ident(Naming.titleCase(name.name)).appended(suffix)

  // class names
  def idName(name: db.RelationName): sc.QIdent = relation(name, "Id")
  def repoName(name: db.RelationName): sc.QIdent = relation(name, "Repo")
  def repoImplName(name: db.RelationName): sc.QIdent = relation(name, "RepoImpl")
  def rowName(name: db.RelationName): sc.QIdent = relation(name, "Row")
  def fieldValueName(name: db.RelationName): sc.QIdent = relation(name, "FieldValue")
  def rowUnsaved(name: db.RelationName): sc.QIdent = relation(name, "RowUnsaved")
  def joinedRow(name: db.RelationName): sc.QIdent = relation(name, "JoinedRow")
  def className(names: List[sc.Ident]): sc.QIdent = pkg / names

  // enum names
  def enumName(name: db.RelationName): sc.QIdent =
    pkg / name.schema.map(sc.Ident.apply).toList / sc.Ident(Naming.titleCase(name.name)).appended("Enum")
  def enumValue(name: String): sc.Ident = sc.Ident(name)

  // field names
  def field(name: db.ColName): sc.Ident =
    Naming.camelCase(name.value.split('_'))

  // multiple field names together into one name
  def field(colNames: List[db.ColName]): sc.Ident =
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
