package typo
package internal
package codegen

case class FileDefault(default: ComputedDefault, jsonLibs: List[JsonLib]) {
  val instances: List[sc.Given] = jsonLibs.flatMap(_.defaultedInstance(default))

  val obj: sc.Code = genObject.withBody(default.Defaulted.value, instances)(
    code"""|case class ${default.Provided}[T](value: T) extends ${default.Defaulted.name}[T]
           |case object ${default.UseDefault} extends ${default.Defaulted.name}[Nothing]""".stripMargin
  )
  val contents =
    code"""
/**
 * This signals a value where if you don't provide it, postgres will generate it for you
 */
sealed trait ${default.Defaulted.name}[+T]

$obj
"""

  val file = sc.File(default.Defaulted, contents, secondaryTypes = Nil)
}
