package typo
package internal
package codegen

case class DefaultFile(default: ComputedDefault, jsonLibs: List[JsonLib]) {
  val contents =
    code"""
/**
 * This signals a value where if you don't provide it, postgres will generate it for you
 */
sealed trait ${default.Defaulted.name}[+T]

object ${default.Defaulted.name} {
  case class ${default.Provided}[T](value: T) extends ${default.Defaulted.name}[T]
  case object ${default.UseDefault} extends ${default.Defaulted.name}[Nothing]
  ${jsonLibs.flatMap(_.defaultedInstance(default)).mkCode("\n")}
}
"""

  val file = sc.File(default.Defaulted, contents, secondaryTypes = Nil)
}
