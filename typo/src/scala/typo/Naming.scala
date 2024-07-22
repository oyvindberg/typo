package typo

class Naming(val pkg: sc.QIdent) {
  protected def fragments(source: Source): (sc.QIdent, String) = {
    def forRelPath(relPath: RelPath): (sc.QIdent, String) =
      (
        pkg / relPath.segments.init.map(pkgSafe),
        relPath.segments.last.replace(".sql", "")
      )

    source match {
      case relation: Source.Relation =>
        (pkg / relation.name.schema.toList.map(pkgSafe), relation.name.name)
      case Source.SqlFile(relPath) => forRelPath(relPath)
    }
  }

  // not enough, but covers a common case
  def pkgSafe(str: String): sc.Ident = sc.Ident(str.replace('-', '_'))

  def suffixFor(source: Source): String =
    source match {
      case Source.Table(_)       => ""
      case Source.View(_, false) => "View"
      case Source.View(_, true)  => "MV"
      case Source.SqlFile(_)     => "Sql"
    }

  protected def relation(source: Source, suffix: String): sc.QIdent = {
    val (init, name) = fragments(source)
    val suffix0 = suffixFor(source)
    init / pkgSafe(name) / sc.Ident(Naming.titleCase(name)).appended(suffix0 + suffix)
  }

  protected def tpe(name: db.RelationName, suffix: String): sc.QIdent =
    pkg / name.schema.map(sc.Ident.apply).toList / sc.Ident(Naming.titleCase(name.name)).appended(suffix)

  def idName(source: Source, cols: List[db.Col]): sc.QIdent = {
    ((), cols)._1 // allow implementors to use this
    relation(source, "Id")
  }
  def repoName(source: Source): sc.QIdent = relation(source, "Repo")
  def repoImplName(source: Source): sc.QIdent = relation(source, "RepoImpl")
  def repoMockName(source: Source): sc.QIdent = relation(source, "RepoMock")
  def rowName(source: Source): sc.QIdent = relation(source, "Row")
  def fieldsName(source: Source): sc.QIdent = relation(source, "Fields")
  def fieldOrIdValueName(source: Source): sc.QIdent = relation(source, "FieldValue")
  def rowUnsaved(source: Source): sc.QIdent = relation(source, "RowUnsaved")

  def className(names: List[sc.Ident]): sc.QIdent = pkg / names

  def enumName(name: db.RelationName): sc.QIdent =
    tpe(name, "")

  def domainName(name: db.RelationName): sc.QIdent =
    tpe(name, "")

  def enumValue(name: String): sc.Ident = sc.Ident(name)

  // field names
  def field(name: db.ColName): sc.Ident =
    Naming.camelCaseIdent(name.value.split('_'))

  def fk(baseTable: db.RelationName, fk: db.ForeignKey, includeCols: Boolean): sc.Ident = {
    val parts = Array[Iterable[String]](
      List("fk"),
      fk.otherTable.schema.filterNot(baseTable.schema.contains),
      List(fk.otherTable.name),
      if (includeCols) fk.cols.toList.flatMap(_.value.split("_")) else Nil
    )
    Naming.camelCaseIdent(parts.flatten)
  }

  // multiple field names together into one name
  def field(colNames: NonEmptyList[db.ColName]): sc.Ident =
    Naming.camelCaseIdent(colNames.map(field).map(_.value).toArray)
}

object Naming {
  def camelCaseIdent(strings: Array[String]): sc.Ident =
    sc.Ident(camelCase(strings))

  def splitOnSymbol(str: String): Array[String] =
    str.split("[\\-_.]")

  def camelCase(strings: Array[String]): String =
    strings
      .flatMap(splitOnSymbol)
      .zipWithIndex
      .map {
        case (s, 0) => s.updated(0, s.head.toLower)
        case (s, _) => s.capitalize
      }
      .mkString("")

  def camelCase(string: String): String =
    camelCase(string.split('_'))

  def titleCase(name: String): String =
    titleCase(name.split('_'))

  def titleCase(strings: Array[String]): String =
    strings.flatMap(splitOnSymbol).map(_.capitalize).mkString("")
}
