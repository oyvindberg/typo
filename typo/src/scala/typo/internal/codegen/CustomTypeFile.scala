package typo
package internal
package codegen

object CustomTypeFile {
  def apply(options: InternalOptions)(et: CustomType): sc.File = {

    val comments = scaladoc(et.comment)(Nil)

    val str =
      code"""$comments
            |case class ${et.typoType.name}(${et.params.map(_.code).mkCode(", ")})
            |object ${et.typoType.name} {
            |  ${options.jsonLibs.flatMap(_.customTypeInstances(et)).mkCode("\n")}
            |  ${options.dbLib.toList.flatMap(_.customTypeInstances(et)).mkCode("\n")}
            |}
""".stripMargin

    sc.File(et.typoType, str, secondaryTypes = Nil)
  }
}
