package typo
package internal
package codegen

object PackageObjectFile {
  def packageObject(options: InternalOptions): sc.File = {
    val parentPkg = NonEmptyList.fromList(options.pkg.idents.dropRight(1))
    val content =
      code"""|${parentPkg.fold(sc.Code.Empty)(nonEmpty => code"package ${nonEmpty.map(_.code).mkCode(".")}")}
             |
             |package object ${options.pkg.name} {
             |  ${options.dbLib.toList.flatMap(_.missingInstances).mkCode("\n")}
             |}
             |""".stripMargin

    sc.File(sc.Type.Qualified(options.pkg / sc.Ident("package")), content, secondaryTypes = Nil)
  }

}
