/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package l

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.location.LocationId
import adventureworks.public.Name
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** View: pr.l */
case class LViewRow(
  /** Points to [[production.location.LocationRow.locationid]] */
  id: LocationId,
  /** Points to [[production.location.LocationRow.locationid]] */
  locationid: LocationId,
  /** Points to [[production.location.LocationRow.name]] */
  name: Name,
  /** Points to [[production.location.LocationRow.costrate]] */
  costrate: BigDecimal,
  /** Points to [[production.location.LocationRow.availability]] */
  availability: BigDecimal,
  /** Points to [[production.location.LocationRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object LViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[LViewRow] = new JdbcDecoder[LViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, LViewRow) =
      columIndex + 5 ->
        LViewRow(
          id = LocationId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          locationid = LocationId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          name = Name.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          costrate = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 3, rs)._2,
          availability = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 4, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 5, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[LViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(LocationId.jsonDecoder))
    val locationid = jsonObj.get("locationid").toRight("Missing field 'locationid'").flatMap(_.as(LocationId.jsonDecoder))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val costrate = jsonObj.get("costrate").toRight("Missing field 'costrate'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val availability = jsonObj.get("availability").toRight("Missing field 'availability'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && locationid.isRight && name.isRight && costrate.isRight && availability.isRight && modifieddate.isRight)
      Right(LViewRow(id = id.toOption.get, locationid = locationid.toOption.get, name = name.toOption.get, costrate = costrate.toOption.get, availability = availability.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, locationid, name, costrate, availability, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[LViewRow] = new JsonEncoder[LViewRow] {
    override def unsafeEncode(a: LViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      LocationId.jsonEncoder.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""locationid":""")
      LocationId.jsonEncoder.unsafeEncode(a.locationid, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""costrate":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.costrate, indent, out)
      out.write(",")
      out.write(""""availability":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.availability, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
}
