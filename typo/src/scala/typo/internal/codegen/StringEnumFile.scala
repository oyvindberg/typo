package typo
package internal
package codegen

object StringEnumFile {
  def apply(naming: Naming, options: InternalOptions)(enm: db.StringEnum): sc.File = {
    val qident = naming.enumName(enm.name)
    val EnumType = sc.Type.Qualified(qident)

    val comments = scaladoc(s"Enum `${enm.name.value}`")(enm.values.map(" - " + _))

    val members = enm.values.map { value =>
      val name = naming.enumValue(value)
      name -> code"case object $name extends ${qident.name}(${sc.StrLit(value)})"
    }
    val str = sc.Ident("str")

    val instances = List(
      options.dbLib.toList.flatMap(_.stringEnumInstances(EnumType, sc.Type.String)),
      options.jsonLibs.flatMap(_.stringEnumInstances(EnumType, sc.Type.String))
    ).flatten

    val obj = genObject.withBody(qident, instances)(
      code"""|def apply($str: ${sc.Type.String}): ${sc.Type.Either.of(sc.Type.String, EnumType)} =
             |  ByName.get($str).toRight(s"'$$str' does not match any of the following legal values: $$Names")
             |def force($str: String): $EnumType =
             |  apply($str) match {
             |    case ${sc.Type.Left}(msg) => sys.error(msg)
             |    case ${sc.Type.Right}(value) => value
             |  }
             |${members.map { case (_, definition) => definition }.mkCode("\n")}
             |val All: ${sc.Type.List.of(EnumType)} = ${sc.Type.List}(${members.map { case (ident, _) => ident.code }.mkCode(", ")})
             |val Names: ${sc.Type.String} = All.map(_.value).mkString(", ")
             |val ByName: ${sc.Type.Map.of(sc.Type.String, EnumType)} = All.map(x => (x.value, x)).toMap
            """.stripMargin
    )
    val body =
      code"""|$comments
             |sealed abstract class ${qident.name}(val value: ${sc.Type.String})
             |
             |$obj
             |""".stripMargin

    sc.File(EnumType, body, secondaryTypes = Nil)
  }
}
