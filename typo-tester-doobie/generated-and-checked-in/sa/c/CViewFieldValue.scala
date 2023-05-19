/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package c

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.customer.CustomerId
import adventureworks.sales.salesterritory.SalesterritoryId
import java.time.LocalDateTime
import java.util.UUID

sealed abstract class CViewFieldOrIdValue[T](val name: String, val value: T)
sealed abstract class CViewFieldValue[T](name: String, value: T) extends CViewFieldOrIdValue(name, value)

object CViewFieldValue {
  case class id(override val value: Option[Int]) extends CViewFieldValue("id", value)
  case class customerid(override val value: Option[CustomerId]) extends CViewFieldValue("customerid", value)
  case class personid(override val value: Option[BusinessentityId]) extends CViewFieldValue("personid", value)
  case class storeid(override val value: Option[BusinessentityId]) extends CViewFieldValue("storeid", value)
  case class territoryid(override val value: Option[SalesterritoryId]) extends CViewFieldValue("territoryid", value)
  case class rowguid(override val value: Option[UUID]) extends CViewFieldValue("rowguid", value)
  case class modifieddate(override val value: Option[LocalDateTime]) extends CViewFieldValue("modifieddate", value)
}