package typo
package internal
package codegen

object CustomTypeFile {
  def apply(options: InternalOptions)(et: CustomType): sc.File = {

    val comments = scaladoc(et.comment)(Nil)

    val instances =
      options.jsonLibs.flatMap(_.customTypeInstances(et)) ++
        options.dbLib.toList.flatMap(_.customTypeInstances(et))

    val str =
      code"""$comments
            |case class ${et.typoType.name}(${et.params.map(_.code).mkCode(", ")})
            |
            |${sc.Obj(et.typoType.value, instances, et.objBody0)}
""".stripMargin

    sc.File(et.typoType, str, secondaryTypes = Nil)
  }
}
