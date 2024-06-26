/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package pnt

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.phonenumbertype.PhonenumbertypeId
import adventureworks.public.Name
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** View: pe.pnt */
case class PntViewRow(
  /** Points to [[person.phonenumbertype.PhonenumbertypeRow.phonenumbertypeid]] */
  id: PhonenumbertypeId,
  /** Points to [[person.phonenumbertype.PhonenumbertypeRow.phonenumbertypeid]] */
  phonenumbertypeid: PhonenumbertypeId,
  /** Points to [[person.phonenumbertype.PhonenumbertypeRow.name]] */
  name: Name,
  /** Points to [[person.phonenumbertype.PhonenumbertypeRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PntViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[PntViewRow] = new JdbcDecoder[PntViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, PntViewRow) =
      columIndex + 3 ->
        PntViewRow(
          id = PhonenumbertypeId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          phonenumbertypeid = PhonenumbertypeId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          name = Name.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[PntViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(PhonenumbertypeId.jsonDecoder))
    val phonenumbertypeid = jsonObj.get("phonenumbertypeid").toRight("Missing field 'phonenumbertypeid'").flatMap(_.as(PhonenumbertypeId.jsonDecoder))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && phonenumbertypeid.isRight && name.isRight && modifieddate.isRight)
      Right(PntViewRow(id = id.toOption.get, phonenumbertypeid = phonenumbertypeid.toOption.get, name = name.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, phonenumbertypeid, name, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[PntViewRow] = new JsonEncoder[PntViewRow] {
    override def unsafeEncode(a: PntViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      PhonenumbertypeId.jsonEncoder.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""phonenumbertypeid":""")
      PhonenumbertypeId.jsonEncoder.unsafeEncode(a.phonenumbertypeid, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
}
