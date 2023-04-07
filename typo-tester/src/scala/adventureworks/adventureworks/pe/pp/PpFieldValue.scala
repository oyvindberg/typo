/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package pp

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.person.phonenumbertype.PhonenumbertypeId
import java.time.LocalDateTime

sealed abstract class PpFieldOrIdValue[T](val name: String, val value: T)
sealed abstract class PpFieldValue[T](name: String, value: T) extends PpFieldOrIdValue(name, value)

object PpFieldValue {
  case class id(override val value: Option[Int]) extends PpFieldValue("id", value)
  case class businessentityid(override val value: Option[BusinessentityId]) extends PpFieldValue("businessentityid", value)
  case class phonenumber(override val value: Option[String]) extends PpFieldValue("phonenumber", value)
  case class phonenumbertypeid(override val value: Option[PhonenumbertypeId]) extends PpFieldValue("phonenumbertypeid", value)
  case class modifieddate(override val value: Option[LocalDateTime]) extends PpFieldValue("modifieddate", value)
}