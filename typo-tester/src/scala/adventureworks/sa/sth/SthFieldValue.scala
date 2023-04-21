/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sth

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.salesterritory.SalesterritoryId
import java.time.LocalDateTime
import java.util.UUID

sealed abstract class SthFieldOrIdValue[T](val name: String, val value: T)
sealed abstract class SthFieldValue[T](name: String, value: T) extends SthFieldOrIdValue(name, value)

object SthFieldValue {
  case class id(override val value: Option[Int]) extends SthFieldValue("id", value)
  case class businessentityid(override val value: Option[BusinessentityId]) extends SthFieldValue("businessentityid", value)
  case class territoryid(override val value: Option[SalesterritoryId]) extends SthFieldValue("territoryid", value)
  case class startdate(override val value: Option[LocalDateTime]) extends SthFieldValue("startdate", value)
  case class enddate(override val value: Option[LocalDateTime]) extends SthFieldValue("enddate", value)
  case class rowguid(override val value: Option[UUID]) extends SthFieldValue("rowguid", value)
  case class modifieddate(override val value: Option[LocalDateTime]) extends SthFieldValue("modifieddate", value)
}