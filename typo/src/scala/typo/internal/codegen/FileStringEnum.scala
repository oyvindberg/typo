package typo
package internal
package codegen

object FileStringEnum {
  def apply(options: InternalOptions, enm: ComputedStringEnum): sc.File = {

    val comments = scaladoc(s"Enum `${enm.name.value}`")(enm.members.map { case (_, v) => " - " + v })

    val members = enm.members.map { case (name, value) =>
      name -> code"case object $name extends ${enm.tpe.name}(${sc.StrLit(value)})"
    }
    val str = sc.Ident("str")

    val instances = List(
      options.dbLib.toList.flatMap(_.stringEnumInstances(enm.tpe, sc.Type.String)),
      options.jsonLibs.flatMap(_.stringEnumInstances(enm.tpe, sc.Type.String))
    ).flatten

    val obj = genObject.withBody(enm.tpe.value, instances)(
      code"""|def apply($str: ${sc.Type.String}): ${sc.Type.Either.of(sc.Type.String, enm.tpe)} =
             |  ByName.get($str).toRight(s"'$$str' does not match any of the following legal values: $$Names")
             |def force($str: String): ${enm.tpe} =
             |  apply($str) match {
             |    case ${sc.Type.Left}(msg) => sys.error(msg)
             |    case ${sc.Type.Right}(value) => value
             |  }
             |${members.map { case (_, definition) => definition }.mkCode("\n")}
             |val All: ${sc.Type.List.of(enm.tpe)} = ${sc.Type.List}(${members.map { case (ident, _) => ident.code }.mkCode(", ")})
             |val Names: ${sc.Type.String} = All.map(_.value).mkString(", ")
             |val ByName: ${sc.Type.Map.of(sc.Type.String, enm.tpe)} = All.map(x => (x.value, x)).toMap
            """.stripMargin
    )
    val body =
      code"""|$comments
             |sealed abstract class ${enm.tpe.name}(val value: ${sc.Type.String})
             |
             |$obj
             |""".stripMargin

    sc.File(enm.tpe, body, secondaryTypes = Nil)
  }
}
