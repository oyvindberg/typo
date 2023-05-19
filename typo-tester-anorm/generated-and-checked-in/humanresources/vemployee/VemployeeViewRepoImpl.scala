/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package vemployee

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.public.Phone
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.RowParser
import anorm.SqlStringInterpolation
import anorm.Success
import java.sql.Connection
import org.postgresql.jdbc.PgSQLXML

object VemployeeViewRepoImpl extends VemployeeViewRepo {
  override def selectAll(implicit c: Connection): List[VemployeeViewRow] = {
    SQL"""select businessentityid, title, firstname, middlename, lastname, suffix, jobtitle, phonenumber, phonenumbertype, emailaddress, emailpromotion, addressline1, addressline2, city, stateprovincename, postalcode, countryregionname, additionalcontactinfo
          from humanresources.vemployee
       """.as(rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[VemployeeViewFieldOrIdValue[_]])(implicit c: Connection): List[VemployeeViewRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case VemployeeViewFieldValue.businessentityid(value) => NamedParameter("businessentityid", ParameterValue.from(value))
          case VemployeeViewFieldValue.title(value) => NamedParameter("title", ParameterValue.from(value))
          case VemployeeViewFieldValue.firstname(value) => NamedParameter("firstname", ParameterValue.from(value))
          case VemployeeViewFieldValue.middlename(value) => NamedParameter("middlename", ParameterValue.from(value))
          case VemployeeViewFieldValue.lastname(value) => NamedParameter("lastname", ParameterValue.from(value))
          case VemployeeViewFieldValue.suffix(value) => NamedParameter("suffix", ParameterValue.from(value))
          case VemployeeViewFieldValue.jobtitle(value) => NamedParameter("jobtitle", ParameterValue.from(value))
          case VemployeeViewFieldValue.phonenumber(value) => NamedParameter("phonenumber", ParameterValue.from(value))
          case VemployeeViewFieldValue.phonenumbertype(value) => NamedParameter("phonenumbertype", ParameterValue.from(value))
          case VemployeeViewFieldValue.emailaddress(value) => NamedParameter("emailaddress", ParameterValue.from(value))
          case VemployeeViewFieldValue.emailpromotion(value) => NamedParameter("emailpromotion", ParameterValue.from(value))
          case VemployeeViewFieldValue.addressline1(value) => NamedParameter("addressline1", ParameterValue.from(value))
          case VemployeeViewFieldValue.addressline2(value) => NamedParameter("addressline2", ParameterValue.from(value))
          case VemployeeViewFieldValue.city(value) => NamedParameter("city", ParameterValue.from(value))
          case VemployeeViewFieldValue.stateprovincename(value) => NamedParameter("stateprovincename", ParameterValue.from(value))
          case VemployeeViewFieldValue.postalcode(value) => NamedParameter("postalcode", ParameterValue.from(value))
          case VemployeeViewFieldValue.countryregionname(value) => NamedParameter("countryregionname", ParameterValue.from(value))
          case VemployeeViewFieldValue.additionalcontactinfo(value) => NamedParameter("additionalcontactinfo", ParameterValue.from(value))
        }
        val quote = '"'.toString
        val q = s"""select businessentityid, title, firstname, middlename, lastname, suffix, jobtitle, phonenumber, phonenumbertype, emailaddress, emailpromotion, addressline1, addressline2, city, stateprovincename, postalcode, countryregionname, additionalcontactinfo
                    from humanresources.vemployee
                    where ${namedParams.map(x => s"$quote${x.name}$quote = {${x.name}}").mkString(" AND ")}
                 """
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .as(rowParser.*)
    }
  
  }
  val rowParser: RowParser[VemployeeViewRow] =
    RowParser[VemployeeViewRow] { row =>
      Success(
        VemployeeViewRow(
          businessentityid = row[Option[BusinessentityId]]("businessentityid"),
          title = row[Option[String]]("title"),
          firstname = row[Option[Name]]("firstname"),
          middlename = row[Option[Name]]("middlename"),
          lastname = row[Option[Name]]("lastname"),
          suffix = row[Option[String]]("suffix"),
          jobtitle = row[Option[String]]("jobtitle"),
          phonenumber = row[Option[Phone]]("phonenumber"),
          phonenumbertype = row[Option[Name]]("phonenumbertype"),
          emailaddress = row[Option[String]]("emailaddress"),
          emailpromotion = row[Option[Int]]("emailpromotion"),
          addressline1 = row[Option[String]]("addressline1"),
          addressline2 = row[Option[String]]("addressline2"),
          city = row[Option[String]]("city"),
          stateprovincename = row[Option[Name]]("stateprovincename"),
          postalcode = row[Option[String]]("postalcode"),
          countryregionname = row[Option[Name]]("countryregionname"),
          additionalcontactinfo = row[Option[PgSQLXML]]("additionalcontactinfo")
        )
      )
    }
}