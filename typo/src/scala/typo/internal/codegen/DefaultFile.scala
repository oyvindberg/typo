package typo
package internal
package codegen

import typo.sc.syntax.CodeInterpolator

case class DefaultFile(dc: DefaultComputed, jsonLib: JsonLib) {
  val contents =
    code"""
/**
 * This signals a value where if you don't provide it, postgres will generate it for you
 */
sealed trait ${dc.Defaulted.name}[+T]

object ${dc.Defaulted.name} {
  case class ${dc.Provided.name}[T](value: T) extends ${dc.Defaulted}[T]
  case object ${dc.UseDefault.name} extends ${dc.Defaulted}[Nothing]
  ${jsonLib.defaultedInstance(dc.Defaulted, dc.Provided.assertInScope, dc.UseDefault.assertInScope).mkCode("\n  ")}
}
"""

  val file = sc.File(dc.DefaultedType, contents)
}
