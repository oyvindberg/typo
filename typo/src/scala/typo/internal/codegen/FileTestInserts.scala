package typo
package internal
package codegen

object FileTestInserts {
  def apply(x: ComputedTestInserts, dbLib: DbLib): List[sc.File] = {
    val params = List(
      Some(sc.Param(ComputedTestInserts.random, TypesScala.Random)),
      x.maybeDomainMethods.map(x => sc.Param(ComputedTestInserts.domainInsert, x.tpe))
    ).flatten

    val cls = sc.Class(
      comments = scaladoc(List(s"Methods to generate random data for `${x.tpe.name}`")),
      classType = sc.ClassType.Class,
      name = x.tpe,
      tparams = Nil,
      params = params,
      implicitParams = Nil,
      `extends` = None,
      implements = Nil,
      members = x.methods.map(dbLib.testInsertMethod),
      staticMembers = Nil
    )

    val maybeDomainMethods =
      x.maybeDomainMethods.map { (x: ComputedTestInserts.GenerateDomainMethods) =>
        val methods = x.methods.map { (method: ComputedTestInserts.GenerateDomainMethod) =>
          val comments = scaladoc {
            val base = s"Domain `${method.dom.underlying.name.value}`"
            method.dom.underlying.constraintDefinition match {
              case Some(definition) => List(base, s"Constraint: $definition")
              case None             => List(base, "No constraint")
            }
          }
          sc.Method(
            comments = comments,
            tparams = Nil,
            name = method.name,
            params = List(sc.Param(ComputedTestInserts.random, TypesScala.Random)),
            implicitParams = Nil,
            tpe = method.dom.tpe,
            body = None
          )
        }

        val cls = sc.Class(
          comments = scaladoc(List(s"Methods to generate random data for domain types")),
          classType = sc.ClassType.Interface,
          name = x.tpe,
          tparams = Nil,
          params = Nil,
          implicitParams = Nil,
          `extends` = None,
          implements = Nil,
          members = methods.toList,
          staticMembers = Nil
        )
        sc.File(x.tpe, cls, Nil, scope = Scope.Test)
      }

    List(Some(sc.File(x.tpe, cls, Nil, scope = Scope.Test)), maybeDomainMethods).flatten
  }
}
