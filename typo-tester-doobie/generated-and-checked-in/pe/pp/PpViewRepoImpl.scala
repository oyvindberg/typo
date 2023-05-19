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
import adventureworks.public.Phone
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import doobie.util.fragments
import fs2.Stream
import java.sql.ResultSet
import java.time.LocalDateTime

object PpViewRepoImpl extends PpViewRepo {
  override def selectAll: Stream[ConnectionIO, PpViewRow] = {
    sql"""select "id", businessentityid, phonenumber, phonenumbertypeid, modifieddate from pe.pp""".query[PpViewRow].stream
  }
  override def selectByFieldValues(fieldValues: List[PpViewFieldOrIdValue[_]]): Stream[ConnectionIO, PpViewRow] = {
    val where = fragments.whereAnd(
      fieldValues.map {
        case PpViewFieldValue.id(value) => fr""""id" = $value"""
        case PpViewFieldValue.businessentityid(value) => fr"businessentityid = $value"
        case PpViewFieldValue.phonenumber(value) => fr"phonenumber = $value"
        case PpViewFieldValue.phonenumbertypeid(value) => fr"phonenumbertypeid = $value"
        case PpViewFieldValue.modifieddate(value) => fr"modifieddate = $value"
      } :_*
    )
    sql"select * from pe.pp $where".query[PpViewRow].stream
  
  }
  implicit val read: Read[PpViewRow] =
    new Read[PpViewRow](
      gets = List(
        (Get[Int], Nullability.Nullable),
        (Get[BusinessentityId], Nullability.Nullable),
        (Get[Phone], Nullability.Nullable),
        (Get[PhonenumbertypeId], Nullability.Nullable),
        (Get[LocalDateTime], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => PpViewRow(
        id = Get[Int].unsafeGetNullable(rs, i + 0),
        businessentityid = Get[BusinessentityId].unsafeGetNullable(rs, i + 1),
        phonenumber = Get[Phone].unsafeGetNullable(rs, i + 2),
        phonenumbertypeid = Get[PhonenumbertypeId].unsafeGetNullable(rs, i + 3),
        modifieddate = Get[LocalDateTime].unsafeGetNullable(rs, i + 4)
      )
    )
  

}