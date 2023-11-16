/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sr

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import adventureworks.sales.salesreason.SalesreasonId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

case class SrViewRow(
  /** Points to [[sales.salesreason.SalesreasonRow.salesreasonid]] */
  id: SalesreasonId,
  /** Points to [[sales.salesreason.SalesreasonRow.salesreasonid]] */
  salesreasonid: SalesreasonId,
  /** Points to [[sales.salesreason.SalesreasonRow.name]] */
  name: Name,
  /** Points to [[sales.salesreason.SalesreasonRow.reasontype]] */
  reasontype: Name,
  /** Points to [[sales.salesreason.SalesreasonRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object SrViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[SrViewRow] = new JdbcDecoder[SrViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, SrViewRow) =
      columIndex + 4 ->
        SrViewRow(
          id = SalesreasonId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          salesreasonid = SalesreasonId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          name = Name.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          reasontype = Name.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[SrViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(SalesreasonId.jsonDecoder))
    val salesreasonid = jsonObj.get("salesreasonid").toRight("Missing field 'salesreasonid'").flatMap(_.as(SalesreasonId.jsonDecoder))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val reasontype = jsonObj.get("reasontype").toRight("Missing field 'reasontype'").flatMap(_.as(Name.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && salesreasonid.isRight && name.isRight && reasontype.isRight && modifieddate.isRight)
      Right(SrViewRow(id = id.toOption.get, salesreasonid = salesreasonid.toOption.get, name = name.toOption.get, reasontype = reasontype.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, salesreasonid, name, reasontype, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[SrViewRow] = new JsonEncoder[SrViewRow] {
    override def unsafeEncode(a: SrViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      SalesreasonId.jsonEncoder.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""salesreasonid":""")
      SalesreasonId.jsonEncoder.unsafeEncode(a.salesreasonid, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""reasontype":""")
      Name.jsonEncoder.unsafeEncode(a.reasontype, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
}