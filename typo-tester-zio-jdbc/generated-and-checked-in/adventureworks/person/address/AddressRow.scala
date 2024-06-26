/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package address

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoBytea
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.stateprovince.StateprovinceId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Table: person.address
    Street address information for customers, employees, and vendors.
    Primary key: addressid */
case class AddressRow(
  /** Primary key for Address records.
      Default: nextval('person.address_addressid_seq'::regclass) */
  addressid: AddressId,
  /** First street address line. */
  addressline1: /* max 60 chars */ String,
  /** Second street address line. */
  addressline2: Option[/* max 60 chars */ String],
  /** Name of the city. */
  city: /* max 30 chars */ String,
  /** Unique identification number for the state or province. Foreign key to StateProvince table.
      Points to [[stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** Postal code for the street address. */
  postalcode: /* max 15 chars */ String,
  /** Latitude and longitude of this address. */
  spatiallocation: Option[TypoBytea],
  /** Default: uuid_generate_v1() */
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = addressid
   def toUnsavedRow(addressid: Defaulted[AddressId], rowguid: Defaulted[TypoUUID] = Defaulted.Provided(this.rowguid), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): AddressRowUnsaved =
     AddressRowUnsaved(addressline1, addressline2, city, stateprovinceid, postalcode, spatiallocation, addressid, rowguid, modifieddate)
 }

object AddressRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[AddressRow] = new JdbcDecoder[AddressRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, AddressRow) =
      columIndex + 8 ->
        AddressRow(
          addressid = AddressId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          addressline1 = JdbcDecoder.stringDecoder.unsafeDecode(columIndex + 1, rs)._2,
          addressline2 = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 2, rs)._2,
          city = JdbcDecoder.stringDecoder.unsafeDecode(columIndex + 3, rs)._2,
          stateprovinceid = StateprovinceId.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2,
          postalcode = JdbcDecoder.stringDecoder.unsafeDecode(columIndex + 5, rs)._2,
          spatiallocation = JdbcDecoder.optionDecoder(TypoBytea.jdbcDecoder).unsafeDecode(columIndex + 6, rs)._2,
          rowguid = TypoUUID.jdbcDecoder.unsafeDecode(columIndex + 7, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 8, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[AddressRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val addressid = jsonObj.get("addressid").toRight("Missing field 'addressid'").flatMap(_.as(AddressId.jsonDecoder))
    val addressline1 = jsonObj.get("addressline1").toRight("Missing field 'addressline1'").flatMap(_.as(JsonDecoder.string))
    val addressline2 = jsonObj.get("addressline2").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val city = jsonObj.get("city").toRight("Missing field 'city'").flatMap(_.as(JsonDecoder.string))
    val stateprovinceid = jsonObj.get("stateprovinceid").toRight("Missing field 'stateprovinceid'").flatMap(_.as(StateprovinceId.jsonDecoder))
    val postalcode = jsonObj.get("postalcode").toRight("Missing field 'postalcode'").flatMap(_.as(JsonDecoder.string))
    val spatiallocation = jsonObj.get("spatiallocation").fold[Either[String, Option[TypoBytea]]](Right(None))(_.as(JsonDecoder.option(using TypoBytea.jsonDecoder)))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(TypoUUID.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (addressid.isRight && addressline1.isRight && addressline2.isRight && city.isRight && stateprovinceid.isRight && postalcode.isRight && spatiallocation.isRight && rowguid.isRight && modifieddate.isRight)
      Right(AddressRow(addressid = addressid.toOption.get, addressline1 = addressline1.toOption.get, addressline2 = addressline2.toOption.get, city = city.toOption.get, stateprovinceid = stateprovinceid.toOption.get, postalcode = postalcode.toOption.get, spatiallocation = spatiallocation.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](addressid, addressline1, addressline2, city, stateprovinceid, postalcode, spatiallocation, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[AddressRow] = new JsonEncoder[AddressRow] {
    override def unsafeEncode(a: AddressRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""addressid":""")
      AddressId.jsonEncoder.unsafeEncode(a.addressid, indent, out)
      out.write(",")
      out.write(""""addressline1":""")
      JsonEncoder.string.unsafeEncode(a.addressline1, indent, out)
      out.write(",")
      out.write(""""addressline2":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.addressline2, indent, out)
      out.write(",")
      out.write(""""city":""")
      JsonEncoder.string.unsafeEncode(a.city, indent, out)
      out.write(",")
      out.write(""""stateprovinceid":""")
      StateprovinceId.jsonEncoder.unsafeEncode(a.stateprovinceid, indent, out)
      out.write(",")
      out.write(""""postalcode":""")
      JsonEncoder.string.unsafeEncode(a.postalcode, indent, out)
      out.write(",")
      out.write(""""spatiallocation":""")
      JsonEncoder.option(using TypoBytea.jsonEncoder).unsafeEncode(a.spatiallocation, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      TypoUUID.jsonEncoder.unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[AddressRow] = Text.instance[AddressRow]{ (row, sb) =>
    AddressId.text.unsafeEncode(row.addressid, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.addressline1, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.addressline2, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.city, sb)
    sb.append(Text.DELIMETER)
    StateprovinceId.text.unsafeEncode(row.stateprovinceid, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.postalcode, sb)
    sb.append(Text.DELIMETER)
    Text.option(TypoBytea.text).unsafeEncode(row.spatiallocation, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
}
