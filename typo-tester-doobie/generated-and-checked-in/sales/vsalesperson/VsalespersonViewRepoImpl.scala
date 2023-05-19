/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vsalesperson

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.public.Phone
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import doobie.util.fragments
import fs2.Stream
import java.sql.ResultSet

object VsalespersonViewRepoImpl extends VsalespersonViewRepo {
  override def selectAll: Stream[ConnectionIO, VsalespersonViewRow] = {
    sql"select businessentityid, title, firstname, middlename, lastname, suffix, jobtitle, phonenumber, phonenumbertype, emailaddress, emailpromotion, addressline1, addressline2, city, stateprovincename, postalcode, countryregionname, territoryname, territorygroup, salesquota, salesytd, saleslastyear from sales.vsalesperson".query[VsalespersonViewRow].stream
  }
  override def selectByFieldValues(fieldValues: List[VsalespersonViewFieldOrIdValue[_]]): Stream[ConnectionIO, VsalespersonViewRow] = {
    val where = fragments.whereAnd(
      fieldValues.map {
        case VsalespersonViewFieldValue.businessentityid(value) => fr"businessentityid = $value"
        case VsalespersonViewFieldValue.title(value) => fr"title = $value"
        case VsalespersonViewFieldValue.firstname(value) => fr"firstname = $value"
        case VsalespersonViewFieldValue.middlename(value) => fr"middlename = $value"
        case VsalespersonViewFieldValue.lastname(value) => fr"lastname = $value"
        case VsalespersonViewFieldValue.suffix(value) => fr"suffix = $value"
        case VsalespersonViewFieldValue.jobtitle(value) => fr"jobtitle = $value"
        case VsalespersonViewFieldValue.phonenumber(value) => fr"phonenumber = $value"
        case VsalespersonViewFieldValue.phonenumbertype(value) => fr"phonenumbertype = $value"
        case VsalespersonViewFieldValue.emailaddress(value) => fr"emailaddress = $value"
        case VsalespersonViewFieldValue.emailpromotion(value) => fr"emailpromotion = $value"
        case VsalespersonViewFieldValue.addressline1(value) => fr"addressline1 = $value"
        case VsalespersonViewFieldValue.addressline2(value) => fr"addressline2 = $value"
        case VsalespersonViewFieldValue.city(value) => fr"city = $value"
        case VsalespersonViewFieldValue.stateprovincename(value) => fr"stateprovincename = $value"
        case VsalespersonViewFieldValue.postalcode(value) => fr"postalcode = $value"
        case VsalespersonViewFieldValue.countryregionname(value) => fr"countryregionname = $value"
        case VsalespersonViewFieldValue.territoryname(value) => fr"territoryname = $value"
        case VsalespersonViewFieldValue.territorygroup(value) => fr"territorygroup = $value"
        case VsalespersonViewFieldValue.salesquota(value) => fr"salesquota = $value"
        case VsalespersonViewFieldValue.salesytd(value) => fr"salesytd = $value"
        case VsalespersonViewFieldValue.saleslastyear(value) => fr"saleslastyear = $value"
      } :_*
    )
    sql"select * from sales.vsalesperson $where".query[VsalespersonViewRow].stream
  
  }
  implicit val read: Read[VsalespersonViewRow] =
    new Read[VsalespersonViewRow](
      gets = List(
        (Get[BusinessentityId], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[Name], Nullability.Nullable),
        (Get[Name], Nullability.Nullable),
        (Get[Name], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[Phone], Nullability.Nullable),
        (Get[Name], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[Int], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[Name], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[Name], Nullability.Nullable),
        (Get[Name], Nullability.Nullable),
        (Get[String], Nullability.Nullable),
        (Get[BigDecimal], Nullability.Nullable),
        (Get[BigDecimal], Nullability.Nullable),
        (Get[BigDecimal], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => VsalespersonViewRow(
        businessentityid = Get[BusinessentityId].unsafeGetNullable(rs, i + 0),
        title = Get[String].unsafeGetNullable(rs, i + 1),
        firstname = Get[Name].unsafeGetNullable(rs, i + 2),
        middlename = Get[Name].unsafeGetNullable(rs, i + 3),
        lastname = Get[Name].unsafeGetNullable(rs, i + 4),
        suffix = Get[String].unsafeGetNullable(rs, i + 5),
        jobtitle = Get[String].unsafeGetNullable(rs, i + 6),
        phonenumber = Get[Phone].unsafeGetNullable(rs, i + 7),
        phonenumbertype = Get[Name].unsafeGetNullable(rs, i + 8),
        emailaddress = Get[String].unsafeGetNullable(rs, i + 9),
        emailpromotion = Get[Int].unsafeGetNullable(rs, i + 10),
        addressline1 = Get[String].unsafeGetNullable(rs, i + 11),
        addressline2 = Get[String].unsafeGetNullable(rs, i + 12),
        city = Get[String].unsafeGetNullable(rs, i + 13),
        stateprovincename = Get[Name].unsafeGetNullable(rs, i + 14),
        postalcode = Get[String].unsafeGetNullable(rs, i + 15),
        countryregionname = Get[Name].unsafeGetNullable(rs, i + 16),
        territoryname = Get[Name].unsafeGetNullable(rs, i + 17),
        territorygroup = Get[String].unsafeGetNullable(rs, i + 18),
        salesquota = Get[BigDecimal].unsafeGetNullable(rs, i + 19),
        salesytd = Get[BigDecimal].unsafeGetNullable(rs, i + 20),
        saleslastyear = Get[BigDecimal].unsafeGetNullable(rs, i + 21)
      )
    )
  

}