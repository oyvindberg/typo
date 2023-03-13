package typo

object names {
  def camelCase(name: db.ColName): sc.Ident =
    sc.Ident(
      name.value
        .split('_')
        .zipWithIndex
        .map {
          case (s, 0) => s
          case (s, _) => s.capitalize
        }
        .mkString("")
    )

  def titleCase(pkg: sc.QIdent, name: String, suffix: String): sc.QIdent =
    pkg / sc.Ident(name.split('_').map(_.capitalize).mkString("")).appended(suffix)

  def EnumName(pkg: sc.QIdent, name: db.EnumName): sc.QIdent = titleCase(pkg, name.value, "Enum")
  def field(name: db.ColName): sc.Ident = camelCase(name)
  def enumValue(name: String): sc.Ident = sc.Ident(name)
}
