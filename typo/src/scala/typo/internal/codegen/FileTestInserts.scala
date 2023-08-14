package typo.internal.codegen

import typo.internal.ComputedTestInserts
import typo.sc

object FileTestInserts {
  def apply(x: ComputedTestInserts, dbLib: DbLib): sc.File = {
    val body =
      code"""|class ${x.tpe.name}(${ComputedTestInserts.random}: ${sc.Type.Random}) {
             |  ${x.methods.map(x => dbLib.testInsertMethod(x).code).mkCode("\n")}
             |}"""

    sc.File(x.tpe, body, Nil)
  }
}
