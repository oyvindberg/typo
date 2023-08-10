package typo.internal.codegen

import typo.internal.ComputedTestInserts
import typo.sc

object FileTestInserts {
  def apply(x: ComputedTestInserts, dbLib: DbLib): sc.File =
    sc.File(x.tpe, genObject(x.tpe.value, x.methods.map(dbLib.testInsertMethod)), Nil)
}
