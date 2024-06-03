package typo
package internal
package codegen

object FileTestInserts {
  def apply(x: ComputedTestInserts, dbLib: DbLib): sc.File = {
    val body =
      code"""|class ${x.tpe.name}(${ComputedTestInserts.random}: ${TypesScala.Random}) {
             |  ${x.methods.map(x => dbLib.testInsertMethod(x).code).mkCode("\n")}
             |}"""

    sc.File(x.tpe, body, Nil, scope = Scope.Test)
  }
}
