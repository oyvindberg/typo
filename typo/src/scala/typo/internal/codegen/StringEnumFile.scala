package typo
package internal
package codegen

object StringEnumFile {
  def apply(naming: Naming, options: InternalOptions)(`enum`: db.StringEnum): sc.File = {
    val qident = naming.enumName(`enum`.name)
    val EnumType = sc.Type.Qualified(qident)

    val members = `enum`.values.map { value =>
      val name = naming.enumValue(value)
      name -> code"case object $name extends ${qident.name}(${sc.StrLit(value)})"
    }
    val ByName = sc.Ident("ByName")
    val str =
      code"""sealed abstract class ${qident.name}(val value: ${sc.Type.String})
            |object ${qident.name} {
            |  ${members.map { case (_, definition) => definition }.mkCode("\n")}
            |
            |  val All: ${sc.Type.List.of(EnumType)} = ${sc.Type.List}(${members.map { case (ident, _) => ident.code }.mkCode(", ")})
            |  val Names: ${sc.Type.String} = All.map(_.value).mkString(", ")
            |  val ByName: ${sc.Type.Map.of(sc.Type.String, EnumType)} = All.map(x => (x.value, x)).toMap
            |
            |  ${options.dbLib.stringEnumInstances(EnumType, sc.Type.String, lookup = ByName).mkCode("\n")}
            |  ${options.jsonLib.stringEnumInstances(EnumType, sc.Type.String, lookup = ByName).mkCode("\n")}
            |}
            |""".stripMargin

    sc.File(EnumType, str, secondaryTypes = Nil)
  }
}
