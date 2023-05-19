/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pu
package sm

import adventureworks.public.Name
import adventureworks.purchasing.shipmethod.ShipmethodId
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import doobie.util.fragments
import fs2.Stream
import java.sql.ResultSet
import java.time.LocalDateTime
import java.util.UUID

object SmViewRepoImpl extends SmViewRepo {
  override def selectAll: Stream[ConnectionIO, SmViewRow] = {
    sql"""select "id", shipmethodid, "name", shipbase, shiprate, rowguid, modifieddate from pu.sm""".query[SmViewRow].stream
  }
  override def selectByFieldValues(fieldValues: List[SmViewFieldOrIdValue[_]]): Stream[ConnectionIO, SmViewRow] = {
    val where = fragments.whereAnd(
      fieldValues.map {
        case SmViewFieldValue.id(value) => fr""""id" = $value"""
        case SmViewFieldValue.shipmethodid(value) => fr"shipmethodid = $value"
        case SmViewFieldValue.name(value) => fr""""name" = $value"""
        case SmViewFieldValue.shipbase(value) => fr"shipbase = $value"
        case SmViewFieldValue.shiprate(value) => fr"shiprate = $value"
        case SmViewFieldValue.rowguid(value) => fr"rowguid = $value"
        case SmViewFieldValue.modifieddate(value) => fr"modifieddate = $value"
      } :_*
    )
    sql"select * from pu.sm $where".query[SmViewRow].stream
  
  }
  implicit val read: Read[SmViewRow] =
    new Read[SmViewRow](
      gets = List(
        (Get[Int], Nullability.Nullable),
        (Get[ShipmethodId], Nullability.Nullable),
        (Get[Name], Nullability.Nullable),
        (Get[BigDecimal], Nullability.Nullable),
        (Get[BigDecimal], Nullability.Nullable),
        (Get[UUID], Nullability.Nullable),
        (Get[LocalDateTime], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => SmViewRow(
        id = Get[Int].unsafeGetNullable(rs, i + 0),
        shipmethodid = Get[ShipmethodId].unsafeGetNullable(rs, i + 1),
        name = Get[Name].unsafeGetNullable(rs, i + 2),
        shipbase = Get[BigDecimal].unsafeGetNullable(rs, i + 3),
        shiprate = Get[BigDecimal].unsafeGetNullable(rs, i + 4),
        rowguid = Get[UUID].unsafeGetNullable(rs, i + 5),
        modifieddate = Get[LocalDateTime].unsafeGetNullable(rs, i + 6)
      )
    )
  

}