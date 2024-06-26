/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package vemployeedepartment

import adventureworks.customtypes.TypoLocalDate
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.userdefined.FirstName
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** View: humanresources.vemployeedepartment */
case class VemployeedepartmentViewRow(
  /** Points to [[employee.EmployeeRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[person.person.PersonRow.title]] */
  title: Option[/* max 8 chars */ String],
  /** Points to [[person.person.PersonRow.firstname]] */
  firstname: /* user-picked */ FirstName,
  /** Points to [[person.person.PersonRow.middlename]] */
  middlename: Option[Name],
  /** Points to [[person.person.PersonRow.lastname]] */
  lastname: Name,
  /** Points to [[person.person.PersonRow.suffix]] */
  suffix: Option[/* max 10 chars */ String],
  /** Points to [[employee.EmployeeRow.jobtitle]] */
  jobtitle: /* max 50 chars */ String,
  /** Points to [[department.DepartmentRow.name]] */
  department: Name,
  /** Points to [[department.DepartmentRow.groupname]] */
  groupname: Name,
  /** Points to [[employeedepartmenthistory.EmployeedepartmenthistoryRow.startdate]] */
  startdate: TypoLocalDate
)

object VemployeedepartmentViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[VemployeedepartmentViewRow] = new JdbcDecoder[VemployeedepartmentViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, VemployeedepartmentViewRow) =
      columIndex + 9 ->
        VemployeedepartmentViewRow(
          businessentityid = BusinessentityId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          title = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 1, rs)._2,
          firstname = FirstName.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          middlename = JdbcDecoder.optionDecoder(Name.jdbcDecoder).unsafeDecode(columIndex + 3, rs)._2,
          lastname = Name.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2,
          suffix = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 5, rs)._2,
          jobtitle = JdbcDecoder.stringDecoder.unsafeDecode(columIndex + 6, rs)._2,
          department = Name.jdbcDecoder.unsafeDecode(columIndex + 7, rs)._2,
          groupname = Name.jdbcDecoder.unsafeDecode(columIndex + 8, rs)._2,
          startdate = TypoLocalDate.jdbcDecoder.unsafeDecode(columIndex + 9, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[VemployeedepartmentViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val title = jsonObj.get("title").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val firstname = jsonObj.get("firstname").toRight("Missing field 'firstname'").flatMap(_.as(FirstName.jsonDecoder))
    val middlename = jsonObj.get("middlename").fold[Either[String, Option[Name]]](Right(None))(_.as(JsonDecoder.option(using Name.jsonDecoder)))
    val lastname = jsonObj.get("lastname").toRight("Missing field 'lastname'").flatMap(_.as(Name.jsonDecoder))
    val suffix = jsonObj.get("suffix").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val jobtitle = jsonObj.get("jobtitle").toRight("Missing field 'jobtitle'").flatMap(_.as(JsonDecoder.string))
    val department = jsonObj.get("department").toRight("Missing field 'department'").flatMap(_.as(Name.jsonDecoder))
    val groupname = jsonObj.get("groupname").toRight("Missing field 'groupname'").flatMap(_.as(Name.jsonDecoder))
    val startdate = jsonObj.get("startdate").toRight("Missing field 'startdate'").flatMap(_.as(TypoLocalDate.jsonDecoder))
    if (businessentityid.isRight && title.isRight && firstname.isRight && middlename.isRight && lastname.isRight && suffix.isRight && jobtitle.isRight && department.isRight && groupname.isRight && startdate.isRight)
      Right(VemployeedepartmentViewRow(businessentityid = businessentityid.toOption.get, title = title.toOption.get, firstname = firstname.toOption.get, middlename = middlename.toOption.get, lastname = lastname.toOption.get, suffix = suffix.toOption.get, jobtitle = jobtitle.toOption.get, department = department.toOption.get, groupname = groupname.toOption.get, startdate = startdate.toOption.get))
    else Left(List[Either[String, Any]](businessentityid, title, firstname, middlename, lastname, suffix, jobtitle, department, groupname, startdate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[VemployeedepartmentViewRow] = new JsonEncoder[VemployeedepartmentViewRow] {
    override def unsafeEncode(a: VemployeedepartmentViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""businessentityid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.businessentityid, indent, out)
      out.write(",")
      out.write(""""title":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.title, indent, out)
      out.write(",")
      out.write(""""firstname":""")
      FirstName.jsonEncoder.unsafeEncode(a.firstname, indent, out)
      out.write(",")
      out.write(""""middlename":""")
      JsonEncoder.option(using Name.jsonEncoder).unsafeEncode(a.middlename, indent, out)
      out.write(",")
      out.write(""""lastname":""")
      Name.jsonEncoder.unsafeEncode(a.lastname, indent, out)
      out.write(",")
      out.write(""""suffix":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.suffix, indent, out)
      out.write(",")
      out.write(""""jobtitle":""")
      JsonEncoder.string.unsafeEncode(a.jobtitle, indent, out)
      out.write(",")
      out.write(""""department":""")
      Name.jsonEncoder.unsafeEncode(a.department, indent, out)
      out.write(",")
      out.write(""""groupname":""")
      Name.jsonEncoder.unsafeEncode(a.groupname, indent, out)
      out.write(",")
      out.write(""""startdate":""")
      TypoLocalDate.jsonEncoder.unsafeEncode(a.startdate, indent, out)
      out.write("}")
    }
  }
}
