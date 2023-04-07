/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package contacttype

import java.time.LocalDateTime

sealed abstract class ContacttypeFieldOrIdValue[T](val name: String, val value: T)
sealed abstract class ContacttypeFieldValue[T](name: String, value: T) extends ContacttypeFieldOrIdValue(name, value)

object ContacttypeFieldValue {
  case class contacttypeid(override val value: ContacttypeId) extends ContacttypeFieldOrIdValue("contacttypeid", value)
  case class name(override val value: String) extends ContacttypeFieldValue("name", value)
  case class modifieddate(override val value: LocalDateTime) extends ContacttypeFieldValue("modifieddate", value)
}