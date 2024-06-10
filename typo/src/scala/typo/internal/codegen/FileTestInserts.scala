package typo
package internal
package codegen

object FileTestInserts {
  def apply(x: ComputedTestInserts, dbLib: DbLib): List[sc.File] = {
    val params = List(
      Some(sc.Param(ComputedTestInserts.random, TypesScala.Random, None)),
      x.maybeDomainMethods.map(x => sc.Param(ComputedTestInserts.domainInsert, x.tpe, None))
    ).flatten

    val body =
      code"""|class ${x.tpe.name}(${params.map(_.code).mkCode(", ")}) {
             |  ${x.methods.map(x => dbLib.testInsertMethod(x).code).mkCode("\n")}
             |}"""

    val maybeDomainMethods = x.maybeDomainMethods.map { (x: ComputedTestInserts.GenerateDomainMethods) =>
      val formattedMethods = x.methods.map { (method: ComputedTestInserts.GenerateDomainMethod) =>
        val comments = scaladoc(s"Domain `${method.dom.underlying.name.value}`")(
          method.dom.underlying.constraintDefinition match {
            case Some(definition) => List(s"Constraint: $definition")
            case None             => List("No constraint")
          }
        )

        code"""|$comments
               |def ${method.name}(${ComputedTestInserts.random}: ${TypesScala.Random}): ${method.dom.tpe}""".stripMargin
      }

      val body =
        code"""|trait ${x.tpe.name} {
               |  ${formattedMethods.mkCode("\n")}
               |}"""
      sc.File(x.tpe, body, Nil, scope = Scope.Test)
    }

    List(Some(sc.File(x.tpe, body, Nil, scope = Scope.Test)), maybeDomainMethods).flatten
  }
}
