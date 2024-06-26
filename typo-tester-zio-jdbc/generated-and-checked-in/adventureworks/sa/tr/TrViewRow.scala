/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package tr

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.stateprovince.StateprovinceId
import adventureworks.public.Name
import adventureworks.sales.salestaxrate.SalestaxrateId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** View: sa.tr */
case class TrViewRow(
  /** Points to [[sales.salestaxrate.SalestaxrateRow.salestaxrateid]] */
  id: SalestaxrateId,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.salestaxrateid]] */
  salestaxrateid: SalestaxrateId,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.taxtype]] */
  taxtype: TypoShort,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.taxrate]] */
  taxrate: BigDecimal,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.name]] */
  name: Name,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object TrViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[TrViewRow] = new JdbcDecoder[TrViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, TrViewRow) =
      columIndex + 7 ->
        TrViewRow(
          id = SalestaxrateId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          salestaxrateid = SalestaxrateId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          stateprovinceid = StateprovinceId.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          taxtype = TypoShort.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          taxrate = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 4, rs)._2,
          name = Name.jdbcDecoder.unsafeDecode(columIndex + 5, rs)._2,
          rowguid = TypoUUID.jdbcDecoder.unsafeDecode(columIndex + 6, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 7, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[TrViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(SalestaxrateId.jsonDecoder))
    val salestaxrateid = jsonObj.get("salestaxrateid").toRight("Missing field 'salestaxrateid'").flatMap(_.as(SalestaxrateId.jsonDecoder))
    val stateprovinceid = jsonObj.get("stateprovinceid").toRight("Missing field 'stateprovinceid'").flatMap(_.as(StateprovinceId.jsonDecoder))
    val taxtype = jsonObj.get("taxtype").toRight("Missing field 'taxtype'").flatMap(_.as(TypoShort.jsonDecoder))
    val taxrate = jsonObj.get("taxrate").toRight("Missing field 'taxrate'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(TypoUUID.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && salestaxrateid.isRight && stateprovinceid.isRight && taxtype.isRight && taxrate.isRight && name.isRight && rowguid.isRight && modifieddate.isRight)
      Right(TrViewRow(id = id.toOption.get, salestaxrateid = salestaxrateid.toOption.get, stateprovinceid = stateprovinceid.toOption.get, taxtype = taxtype.toOption.get, taxrate = taxrate.toOption.get, name = name.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, salestaxrateid, stateprovinceid, taxtype, taxrate, name, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[TrViewRow] = new JsonEncoder[TrViewRow] {
    override def unsafeEncode(a: TrViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      SalestaxrateId.jsonEncoder.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""salestaxrateid":""")
      SalestaxrateId.jsonEncoder.unsafeEncode(a.salestaxrateid, indent, out)
      out.write(",")
      out.write(""""stateprovinceid":""")
      StateprovinceId.jsonEncoder.unsafeEncode(a.stateprovinceid, indent, out)
      out.write(",")
      out.write(""""taxtype":""")
      TypoShort.jsonEncoder.unsafeEncode(a.taxtype, indent, out)
      out.write(",")
      out.write(""""taxrate":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.taxrate, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
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
