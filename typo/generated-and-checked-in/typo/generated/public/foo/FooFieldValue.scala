/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources`)
 */
package typo
package generated
package public
package foo



sealed abstract class FooFieldOrIdValue[T](val name: String, val value: T)
sealed abstract class FooFieldValue[T](name: String, value: T) extends FooFieldOrIdValue(name, value)

object FooFieldValue {
  case class arrayAgg(override val value: Option[Array[String]]) extends FooFieldValue("array_agg", value)
}