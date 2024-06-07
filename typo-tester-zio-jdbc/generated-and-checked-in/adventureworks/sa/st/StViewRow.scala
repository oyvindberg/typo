/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package st

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Name
import adventureworks.sales.salesterritory.SalesterritoryId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** View: sa.st */
case class StViewRow(
  /** Points to [[sales.salesterritory.SalesterritoryRow.territoryid]] */
  id: SalesterritoryId,
  /** Points to [[sales.salesterritory.SalesterritoryRow.territoryid]] */
  territoryid: SalesterritoryId,
  /** Points to [[sales.salesterritory.SalesterritoryRow.name]] */
  name: Name,
  /** Points to [[sales.salesterritory.SalesterritoryRow.countryregioncode]] */
  countryregioncode: CountryregionId,
  /** Points to [[sales.salesterritory.SalesterritoryRow.group]] */
  group: /* max 50 chars */ String,
  /** Points to [[sales.salesterritory.SalesterritoryRow.salesytd]] */
  salesytd: BigDecimal,
  /** Points to [[sales.salesterritory.SalesterritoryRow.saleslastyear]] */
  saleslastyear: BigDecimal,
  /** Points to [[sales.salesterritory.SalesterritoryRow.costytd]] */
  costytd: BigDecimal,
  /** Points to [[sales.salesterritory.SalesterritoryRow.costlastyear]] */
  costlastyear: BigDecimal,
  /** Points to [[sales.salesterritory.SalesterritoryRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[sales.salesterritory.SalesterritoryRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object StViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[StViewRow] = new JdbcDecoder[StViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, StViewRow) =
      columIndex + 10 ->
        StViewRow(
          id = SalesterritoryId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          territoryid = SalesterritoryId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          name = Name.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          countryregioncode = CountryregionId.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          group = JdbcDecoder.stringDecoder.unsafeDecode(columIndex + 4, rs)._2,
          salesytd = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 5, rs)._2,
          saleslastyear = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 6, rs)._2,
          costytd = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 7, rs)._2,
          costlastyear = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 8, rs)._2,
          rowguid = TypoUUID.jdbcDecoder.unsafeDecode(columIndex + 9, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 10, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[StViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(SalesterritoryId.jsonDecoder))
    val territoryid = jsonObj.get("territoryid").toRight("Missing field 'territoryid'").flatMap(_.as(SalesterritoryId.jsonDecoder))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val countryregioncode = jsonObj.get("countryregioncode").toRight("Missing field 'countryregioncode'").flatMap(_.as(CountryregionId.jsonDecoder))
    val group = jsonObj.get("group").toRight("Missing field 'group'").flatMap(_.as(JsonDecoder.string))
    val salesytd = jsonObj.get("salesytd").toRight("Missing field 'salesytd'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val saleslastyear = jsonObj.get("saleslastyear").toRight("Missing field 'saleslastyear'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val costytd = jsonObj.get("costytd").toRight("Missing field 'costytd'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val costlastyear = jsonObj.get("costlastyear").toRight("Missing field 'costlastyear'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(TypoUUID.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && territoryid.isRight && name.isRight && countryregioncode.isRight && group.isRight && salesytd.isRight && saleslastyear.isRight && costytd.isRight && costlastyear.isRight && rowguid.isRight && modifieddate.isRight)
      Right(StViewRow(id = id.toOption.get, territoryid = territoryid.toOption.get, name = name.toOption.get, countryregioncode = countryregioncode.toOption.get, group = group.toOption.get, salesytd = salesytd.toOption.get, saleslastyear = saleslastyear.toOption.get, costytd = costytd.toOption.get, costlastyear = costlastyear.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, territoryid, name, countryregioncode, group, salesytd, saleslastyear, costytd, costlastyear, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[StViewRow] = new JsonEncoder[StViewRow] {
    override def unsafeEncode(a: StViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      SalesterritoryId.jsonEncoder.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""territoryid":""")
      SalesterritoryId.jsonEncoder.unsafeEncode(a.territoryid, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""countryregioncode":""")
      CountryregionId.jsonEncoder.unsafeEncode(a.countryregioncode, indent, out)
      out.write(",")
      out.write(""""group":""")
      JsonEncoder.string.unsafeEncode(a.group, indent, out)
      out.write(",")
      out.write(""""salesytd":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.salesytd, indent, out)
      out.write(",")
      out.write(""""saleslastyear":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.saleslastyear, indent, out)
      out.write(",")
      out.write(""""costytd":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.costytd, indent, out)
      out.write(",")
      out.write(""""costlastyear":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.costlastyear, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      TypoUUID.jsonEncoder.unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
}