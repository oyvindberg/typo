/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sohsr

import adventureworks.sales.salesorderheader.SalesorderheaderId
import adventureworks.sales.salesreason.SalesreasonId
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import doobie.util.fragments
import fs2.Stream
import java.sql.ResultSet
import java.time.LocalDateTime

object SohsrViewRepoImpl extends SohsrViewRepo {
  override def selectAll: Stream[ConnectionIO, SohsrViewRow] = {
    sql"select salesorderid, salesreasonid, modifieddate from sa.sohsr".query[SohsrViewRow].stream
  }
  override def selectByFieldValues(fieldValues: List[SohsrViewFieldOrIdValue[_]]): Stream[ConnectionIO, SohsrViewRow] = {
    val where = fragments.whereAnd(
      fieldValues.map {
        case SohsrViewFieldValue.salesorderid(value) => fr"salesorderid = $value"
        case SohsrViewFieldValue.salesreasonid(value) => fr"salesreasonid = $value"
        case SohsrViewFieldValue.modifieddate(value) => fr"modifieddate = $value"
      } :_*
    )
    sql"select * from sa.sohsr $where".query[SohsrViewRow].stream
  
  }
  implicit val read: Read[SohsrViewRow] =
    new Read[SohsrViewRow](
      gets = List(
        (Get[SalesorderheaderId], Nullability.Nullable),
        (Get[SalesreasonId], Nullability.Nullable),
        (Get[LocalDateTime], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => SohsrViewRow(
        salesorderid = Get[SalesorderheaderId].unsafeGetNullable(rs, i + 0),
        salesreasonid = Get[SalesreasonId].unsafeGetNullable(rs, i + 1),
        modifieddate = Get[LocalDateTime].unsafeGetNullable(rs, i + 2)
      )
    )
  

}