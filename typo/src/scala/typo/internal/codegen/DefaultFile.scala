package typo
package internal
package codegen

case class DefaultFile(dc: DefaultComputed, jsonLib: JsonLib) {
  val contents =
    code"""
/**
 * This signals a value where if you don't provide it, postgres will generate it for you
 */
sealed trait ${dc.Defaulted.name}[+T]

object ${dc.Defaulted.name} {
  case class ${dc.Provided}[T](value: T) extends ${dc.Defaulted.name}[T]
  case object ${dc.UseDefault} extends ${dc.Defaulted.name}[Nothing]
  ${jsonLib.defaultedInstance(dc.Defaulted, dc.Provided, dc.UseDefault).mkCode("\n")}
}
"""

  val file = sc.File(dc.Defaulted, contents, secondaryTypes = Nil)
}
