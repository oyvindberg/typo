/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vsalespersonsalesbyfiscalyearsdata

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

case class VsalespersonsalesbyfiscalyearsdataViewRow(
  /** Points to [[salesorderheader.SalesorderheaderRow.salespersonid]] */
  salespersonid: Option[BusinessentityId],
  fullname: /* nullability unknown */ Option[String],
  /** Points to [[humanresources.employee.EmployeeRow.jobtitle]] */
  jobtitle: /* max 50 chars */ String,
  /** Points to [[salesterritory.SalesterritoryRow.name]] */
  salesterritory: Name,
  salestotal: /* nullability unknown */ Option[BigDecimal],
  fiscalyear: /* nullability unknown */ Option[BigDecimal]
)

object VsalespersonsalesbyfiscalyearsdataViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[VsalespersonsalesbyfiscalyearsdataViewRow] = new JdbcDecoder[VsalespersonsalesbyfiscalyearsdataViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, VsalespersonsalesbyfiscalyearsdataViewRow) =
      columIndex + 5 ->
        VsalespersonsalesbyfiscalyearsdataViewRow(
          salespersonid = JdbcDecoder.optionDecoder(BusinessentityId.jdbcDecoder).unsafeDecode(columIndex + 0, rs)._2,
          fullname = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 1, rs)._2,
          jobtitle = JdbcDecoder.stringDecoder.unsafeDecode(columIndex + 2, rs)._2,
          salesterritory = Name.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          salestotal = JdbcDecoder.optionDecoder(JdbcDecoder.bigDecimalDecoderScala).unsafeDecode(columIndex + 4, rs)._2,
          fiscalyear = JdbcDecoder.optionDecoder(JdbcDecoder.bigDecimalDecoderScala).unsafeDecode(columIndex + 5, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[VsalespersonsalesbyfiscalyearsdataViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val salespersonid = jsonObj.get("salespersonid").fold[Either[String, Option[BusinessentityId]]](Right(None))(_.as(JsonDecoder.option(using BusinessentityId.jsonDecoder)))
    val fullname = jsonObj.get("fullname").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val jobtitle = jsonObj.get("jobtitle").toRight("Missing field 'jobtitle'").flatMap(_.as(JsonDecoder.string))
    val salesterritory = jsonObj.get("salesterritory").toRight("Missing field 'salesterritory'").flatMap(_.as(Name.jsonDecoder))
    val salestotal = jsonObj.get("salestotal").fold[Either[String, Option[BigDecimal]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.scalaBigDecimal)))
    val fiscalyear = jsonObj.get("fiscalyear").fold[Either[String, Option[BigDecimal]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.scalaBigDecimal)))
    if (salespersonid.isRight && fullname.isRight && jobtitle.isRight && salesterritory.isRight && salestotal.isRight && fiscalyear.isRight)
      Right(VsalespersonsalesbyfiscalyearsdataViewRow(salespersonid = salespersonid.toOption.get, fullname = fullname.toOption.get, jobtitle = jobtitle.toOption.get, salesterritory = salesterritory.toOption.get, salestotal = salestotal.toOption.get, fiscalyear = fiscalyear.toOption.get))
    else Left(List[Either[String, Any]](salespersonid, fullname, jobtitle, salesterritory, salestotal, fiscalyear).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[VsalespersonsalesbyfiscalyearsdataViewRow] = new JsonEncoder[VsalespersonsalesbyfiscalyearsdataViewRow] {
    override def unsafeEncode(a: VsalespersonsalesbyfiscalyearsdataViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""salespersonid":""")
      JsonEncoder.option(using BusinessentityId.jsonEncoder).unsafeEncode(a.salespersonid, indent, out)
      out.write(",")
      out.write(""""fullname":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.fullname, indent, out)
      out.write(",")
      out.write(""""jobtitle":""")
      JsonEncoder.string.unsafeEncode(a.jobtitle, indent, out)
      out.write(",")
      out.write(""""salesterritory":""")
      Name.jsonEncoder.unsafeEncode(a.salesterritory, indent, out)
      out.write(",")
      out.write(""""salestotal":""")
      JsonEncoder.option(using JsonEncoder.scalaBigDecimal).unsafeEncode(a.salestotal, indent, out)
      out.write(",")
      out.write(""""fiscalyear":""")
      JsonEncoder.option(using JsonEncoder.scalaBigDecimal).unsafeEncode(a.fiscalyear, indent, out)
      out.write("}")
    }
  }
}