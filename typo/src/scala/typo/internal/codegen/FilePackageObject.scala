package typo
package internal
package codegen

object FilePackageObject {
  def packageObject(options: InternalOptions): Option[sc.File] = {
    val parentPkg = NonEmptyList.fromList(options.pkg.idents.dropRight(1))
    val instances = options.dbLib.toList.flatMap(_.missingInstances) ++ options.jsonLibs.flatMap(_.missingInstances)
    if (instances.isEmpty) None
    else {
      val content =
        code"""|${parentPkg.fold(sc.Code.Empty)(nonEmpty => code"package ${nonEmpty.map(_.code).mkCode(".")}")}
               |
               |package object ${options.pkg.name} {
               |  ${instances.sortBy(_.name).map(_.code).mkCode("\n")}
               |}
               |""".stripMargin

      Some(sc.File(sc.Type.Qualified(options.pkg / sc.Ident("package")), content, secondaryTypes = Nil, scope = Scope.Main))
    }
  }
}
