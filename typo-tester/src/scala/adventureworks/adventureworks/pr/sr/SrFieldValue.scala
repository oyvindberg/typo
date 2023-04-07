/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package sr

import adventureworks.production.scrapreason.ScrapreasonId
import java.time.LocalDateTime

sealed abstract class SrFieldOrIdValue[T](val name: String, val value: T)
sealed abstract class SrFieldValue[T](name: String, value: T) extends SrFieldOrIdValue(name, value)

object SrFieldValue {
  case class id(override val value: Option[Int]) extends SrFieldValue("id", value)
  case class scrapreasonid(override val value: Option[ScrapreasonId]) extends SrFieldValue("scrapreasonid", value)
  case class name(override val value: Option[String]) extends SrFieldValue("name", value)
  case class modifieddate(override val value: Option[LocalDateTime]) extends SrFieldValue("modifieddate", value)
}