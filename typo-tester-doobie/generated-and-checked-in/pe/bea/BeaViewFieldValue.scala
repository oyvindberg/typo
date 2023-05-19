/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package bea

import adventureworks.person.address.AddressId
import adventureworks.person.addresstype.AddresstypeId
import adventureworks.person.businessentity.BusinessentityId
import java.time.LocalDateTime
import java.util.UUID

sealed abstract class BeaViewFieldOrIdValue[T](val name: String, val value: T)
sealed abstract class BeaViewFieldValue[T](name: String, value: T) extends BeaViewFieldOrIdValue(name, value)

object BeaViewFieldValue {
  case class id(override val value: Option[Int]) extends BeaViewFieldValue("id", value)
  case class businessentityid(override val value: Option[BusinessentityId]) extends BeaViewFieldValue("businessentityid", value)
  case class addressid(override val value: Option[AddressId]) extends BeaViewFieldValue("addressid", value)
  case class addresstypeid(override val value: Option[AddresstypeId]) extends BeaViewFieldValue("addresstypeid", value)
  case class rowguid(override val value: Option[UUID]) extends BeaViewFieldValue("rowguid", value)
  case class modifieddate(override val value: Option[LocalDateTime]) extends BeaViewFieldValue("modifieddate", value)
}