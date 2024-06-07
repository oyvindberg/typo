/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package bec

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.person.contacttype.ContacttypeId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** View: pe.bec */
case class BecViewRow(
  /** Points to [[person.businessentitycontact.BusinessentitycontactRow.businessentityid]] */
  id: BusinessentityId,
  /** Points to [[person.businessentitycontact.BusinessentitycontactRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[person.businessentitycontact.BusinessentitycontactRow.personid]] */
  personid: BusinessentityId,
  /** Points to [[person.businessentitycontact.BusinessentitycontactRow.contacttypeid]] */
  contacttypeid: ContacttypeId,
  /** Points to [[person.businessentitycontact.BusinessentitycontactRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[person.businessentitycontact.BusinessentitycontactRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object BecViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[BecViewRow] = new JdbcDecoder[BecViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, BecViewRow) =
      columIndex + 5 ->
        BecViewRow(
          id = BusinessentityId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          businessentityid = BusinessentityId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          personid = BusinessentityId.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          contacttypeid = ContacttypeId.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          rowguid = TypoUUID.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 5, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[BecViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val personid = jsonObj.get("personid").toRight("Missing field 'personid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val contacttypeid = jsonObj.get("contacttypeid").toRight("Missing field 'contacttypeid'").flatMap(_.as(ContacttypeId.jsonDecoder))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(TypoUUID.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && businessentityid.isRight && personid.isRight && contacttypeid.isRight && rowguid.isRight && modifieddate.isRight)
      Right(BecViewRow(id = id.toOption.get, businessentityid = businessentityid.toOption.get, personid = personid.toOption.get, contacttypeid = contacttypeid.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, businessentityid, personid, contacttypeid, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[BecViewRow] = new JsonEncoder[BecViewRow] {
    override def unsafeEncode(a: BecViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""businessentityid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.businessentityid, indent, out)
      out.write(",")
      out.write(""""personid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.personid, indent, out)
      out.write(",")
      out.write(""""contacttypeid":""")
      ContacttypeId.jsonEncoder.unsafeEncode(a.contacttypeid, indent, out)
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