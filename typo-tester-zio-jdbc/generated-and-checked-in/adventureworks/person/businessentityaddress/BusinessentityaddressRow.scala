/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentityaddress

import adventureworks.Text
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.address.AddressId
import adventureworks.person.addresstype.AddresstypeId
import adventureworks.person.businessentity.BusinessentityId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

case class BusinessentityaddressRow(
  /** Primary key. Foreign key to BusinessEntity.BusinessEntityID.
      Points to [[businessentity.BusinessentityRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Primary key. Foreign key to Address.AddressID.
      Points to [[address.AddressRow.addressid]] */
  addressid: AddressId,
  /** Primary key. Foreign key to AddressType.AddressTypeID.
      Points to [[addresstype.AddresstypeRow.addresstypeid]] */
  addresstypeid: AddresstypeId,
  /** Default: uuid_generate_v1() */
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val compositeId: BusinessentityaddressId = BusinessentityaddressId(businessentityid, addressid, addresstypeid)
 }

object BusinessentityaddressRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[BusinessentityaddressRow] = new JdbcDecoder[BusinessentityaddressRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, BusinessentityaddressRow) =
      columIndex + 4 ->
        BusinessentityaddressRow(
          businessentityid = BusinessentityId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          addressid = AddressId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          addresstypeid = AddresstypeId.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          rowguid = TypoUUID.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[BusinessentityaddressRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val addressid = jsonObj.get("addressid").toRight("Missing field 'addressid'").flatMap(_.as(AddressId.jsonDecoder))
    val addresstypeid = jsonObj.get("addresstypeid").toRight("Missing field 'addresstypeid'").flatMap(_.as(AddresstypeId.jsonDecoder))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(TypoUUID.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (businessentityid.isRight && addressid.isRight && addresstypeid.isRight && rowguid.isRight && modifieddate.isRight)
      Right(BusinessentityaddressRow(businessentityid = businessentityid.toOption.get, addressid = addressid.toOption.get, addresstypeid = addresstypeid.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](businessentityid, addressid, addresstypeid, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[BusinessentityaddressRow] = new JsonEncoder[BusinessentityaddressRow] {
    override def unsafeEncode(a: BusinessentityaddressRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""businessentityid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.businessentityid, indent, out)
      out.write(",")
      out.write(""""addressid":""")
      AddressId.jsonEncoder.unsafeEncode(a.addressid, indent, out)
      out.write(",")
      out.write(""""addresstypeid":""")
      AddresstypeId.jsonEncoder.unsafeEncode(a.addresstypeid, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      TypoUUID.jsonEncoder.unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[BusinessentityaddressRow] = Text.instance[BusinessentityaddressRow]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    AddressId.text.unsafeEncode(row.addressid, sb)
    sb.append(Text.DELIMETER)
    AddresstypeId.text.unsafeEncode(row.addresstypeid, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
}