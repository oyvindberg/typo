/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vstorewithdemographics

import adventureworks.customtypes.TypoMoney
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

case class VstorewithdemographicsViewRow(
  /** Points to [[store.StoreRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[store.StoreRow.name]] */
  name: Name,
  AnnualSales: /* nullability unknown */ Option[TypoMoney],
  AnnualRevenue: /* nullability unknown */ Option[TypoMoney],
  BankName: /* nullability unknown */ Option[/* max 50 chars */ String],
  BusinessType: /* nullability unknown */ Option[/* max 5 chars */ String],
  YearOpened: /* nullability unknown */ Option[Int],
  Specialty: /* nullability unknown */ Option[/* max 50 chars */ String],
  SquareFeet: /* nullability unknown */ Option[Int],
  Brands: /* nullability unknown */ Option[/* max 30 chars */ String],
  Internet: /* nullability unknown */ Option[/* max 30 chars */ String],
  NumberEmployees: /* nullability unknown */ Option[Int]
)

object VstorewithdemographicsViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[VstorewithdemographicsViewRow] = new JdbcDecoder[VstorewithdemographicsViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, VstorewithdemographicsViewRow) =
      columIndex + 11 ->
        VstorewithdemographicsViewRow(
          businessentityid = BusinessentityId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          name = Name.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          AnnualSales = JdbcDecoder.optionDecoder(TypoMoney.jdbcDecoder).unsafeDecode(columIndex + 2, rs)._2,
          AnnualRevenue = JdbcDecoder.optionDecoder(TypoMoney.jdbcDecoder).unsafeDecode(columIndex + 3, rs)._2,
          BankName = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 4, rs)._2,
          BusinessType = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 5, rs)._2,
          YearOpened = JdbcDecoder.optionDecoder(JdbcDecoder.intDecoder).unsafeDecode(columIndex + 6, rs)._2,
          Specialty = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 7, rs)._2,
          SquareFeet = JdbcDecoder.optionDecoder(JdbcDecoder.intDecoder).unsafeDecode(columIndex + 8, rs)._2,
          Brands = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 9, rs)._2,
          Internet = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 10, rs)._2,
          NumberEmployees = JdbcDecoder.optionDecoder(JdbcDecoder.intDecoder).unsafeDecode(columIndex + 11, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[VstorewithdemographicsViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val AnnualSales = jsonObj.get("AnnualSales").fold[Either[String, Option[TypoMoney]]](Right(None))(_.as(JsonDecoder.option(using TypoMoney.jsonDecoder)))
    val AnnualRevenue = jsonObj.get("AnnualRevenue").fold[Either[String, Option[TypoMoney]]](Right(None))(_.as(JsonDecoder.option(using TypoMoney.jsonDecoder)))
    val BankName = jsonObj.get("BankName").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val BusinessType = jsonObj.get("BusinessType").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val YearOpened = jsonObj.get("YearOpened").fold[Either[String, Option[Int]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.int)))
    val Specialty = jsonObj.get("Specialty").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val SquareFeet = jsonObj.get("SquareFeet").fold[Either[String, Option[Int]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.int)))
    val Brands = jsonObj.get("Brands").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val Internet = jsonObj.get("Internet").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val NumberEmployees = jsonObj.get("NumberEmployees").fold[Either[String, Option[Int]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.int)))
    if (businessentityid.isRight && name.isRight && AnnualSales.isRight && AnnualRevenue.isRight && BankName.isRight && BusinessType.isRight && YearOpened.isRight && Specialty.isRight && SquareFeet.isRight && Brands.isRight && Internet.isRight && NumberEmployees.isRight)
      Right(VstorewithdemographicsViewRow(businessentityid = businessentityid.toOption.get, name = name.toOption.get, AnnualSales = AnnualSales.toOption.get, AnnualRevenue = AnnualRevenue.toOption.get, BankName = BankName.toOption.get, BusinessType = BusinessType.toOption.get, YearOpened = YearOpened.toOption.get, Specialty = Specialty.toOption.get, SquareFeet = SquareFeet.toOption.get, Brands = Brands.toOption.get, Internet = Internet.toOption.get, NumberEmployees = NumberEmployees.toOption.get))
    else Left(List[Either[String, Any]](businessentityid, name, AnnualSales, AnnualRevenue, BankName, BusinessType, YearOpened, Specialty, SquareFeet, Brands, Internet, NumberEmployees).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[VstorewithdemographicsViewRow] = new JsonEncoder[VstorewithdemographicsViewRow] {
    override def unsafeEncode(a: VstorewithdemographicsViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""businessentityid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.businessentityid, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""AnnualSales":""")
      JsonEncoder.option(using TypoMoney.jsonEncoder).unsafeEncode(a.AnnualSales, indent, out)
      out.write(",")
      out.write(""""AnnualRevenue":""")
      JsonEncoder.option(using TypoMoney.jsonEncoder).unsafeEncode(a.AnnualRevenue, indent, out)
      out.write(",")
      out.write(""""BankName":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.BankName, indent, out)
      out.write(",")
      out.write(""""BusinessType":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.BusinessType, indent, out)
      out.write(",")
      out.write(""""YearOpened":""")
      JsonEncoder.option(using JsonEncoder.int).unsafeEncode(a.YearOpened, indent, out)
      out.write(",")
      out.write(""""Specialty":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.Specialty, indent, out)
      out.write(",")
      out.write(""""SquareFeet":""")
      JsonEncoder.option(using JsonEncoder.int).unsafeEncode(a.SquareFeet, indent, out)
      out.write(",")
      out.write(""""Brands":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.Brands, indent, out)
      out.write(",")
      out.write(""""Internet":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.Internet, indent, out)
      out.write(",")
      out.write(""""NumberEmployees":""")
      JsonEncoder.option(using JsonEncoder.int).unsafeEncode(a.NumberEmployees, indent, out)
      out.write("}")
    }
  }
}
