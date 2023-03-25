package typo

object names {
  def camelCase(strings: Array[String]): sc.Ident =
    sc.Ident(
      strings.zipWithIndex
        .map {
          case (s, 0) => s
          case (s, _) => s.capitalize
        }
        .mkString("")
    )

  def camelCase(name: db.ColName): sc.Ident =
    camelCase(name.value.split('_'))

  def titleCase(name: String): String =
    name.split('_').map(_.capitalize).mkString("")

  def titleCase(pkg: sc.QIdent, name: db.RelationName, suffix: String): sc.QIdent =
    pkg / name.schema.map(sc.Ident.apply).toList / sc.Ident(titleCase(name.name)).appended(suffix)

  def EnumName(pkg: sc.QIdent, name: db.RelationName): sc.QIdent =
    pkg / name.schema.map(sc.Ident.apply).toList / sc.Ident(titleCase(name.name)).appended("Enum")

  def field(name: db.ColName): sc.Ident = camelCase(name)
  def enumValue(name: String): sc.Ident = sc.Ident(name)
}
