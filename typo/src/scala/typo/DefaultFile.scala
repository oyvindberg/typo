package typo

import typo.sc.syntax.CodeInterpolator

case class DefaultFile(dc: DefaultComputed, jsonLib: JsonLib) {
  val contents =
    code"""
/**
 * This signals a value where if you don't provide it, postgres will generate it for you
 */
sealed trait ${dc.Defaulted.last}[+T]

object ${dc.Defaulted.last} {
  case class ${dc.Provided.last}[T](value: T) extends ${dc.Defaulted}[T]
  case object ${dc.UseDefault.last} extends ${dc.Defaulted}[Nothing]
  ${jsonLib.defaultedInstance(dc.Defaulted, dc.Provided, dc.UseDefault).mkCode("\n  ")}
}
"""

  val file = sc.File(dc.DefaultedType, contents)
}
