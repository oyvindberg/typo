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
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** This class corresponds to a row in table `person.address` which has not been persisted yet */
case class AddressRowUnsaved(
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
  /** Default: nextval('person.address_addressid_seq'::regclass)
      Primary key for Address records. */
  addressid: Defaulted[AddressId] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(addressidDefault: => AddressId, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): AddressRow =
    AddressRow(
      addressline1 = addressline1,
      addressline2 = addressline2,
      city = city,
      stateprovinceid = stateprovinceid,
      postalcode = postalcode,
      spatiallocation = spatiallocation,
      addressid = addressid match {
                    case Defaulted.UseDefault => addressidDefault
                    case Defaulted.Provided(value) => value
                  },
      rowguid = rowguid match {
                  case Defaulted.UseDefault => rowguidDefault
                  case Defaulted.Provided(value) => value
                },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object AddressRowUnsaved {
  implicit lazy val jsonDecoder: JsonDecoder[AddressRowUnsaved] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val addressline1 = jsonObj.get("addressline1").toRight("Missing field 'addressline1'").flatMap(_.as(JsonDecoder.string))
    val addressline2 = jsonObj.get("addressline2").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val city = jsonObj.get("city").toRight("Missing field 'city'").flatMap(_.as(JsonDecoder.string))
    val stateprovinceid = jsonObj.get("stateprovinceid").toRight("Missing field 'stateprovinceid'").flatMap(_.as(StateprovinceId.jsonDecoder))
    val postalcode = jsonObj.get("postalcode").toRight("Missing field 'postalcode'").flatMap(_.as(JsonDecoder.string))
    val spatiallocation = jsonObj.get("spatiallocation").fold[Either[String, Option[TypoBytea]]](Right(None))(_.as(JsonDecoder.option(using TypoBytea.jsonDecoder)))
    val addressid = jsonObj.get("addressid").toRight("Missing field 'addressid'").flatMap(_.as(Defaulted.jsonDecoder(AddressId.jsonDecoder)))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(Defaulted.jsonDecoder(TypoUUID.jsonDecoder)))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(Defaulted.jsonDecoder(TypoLocalDateTime.jsonDecoder)))
    if (addressline1.isRight && addressline2.isRight && city.isRight && stateprovinceid.isRight && postalcode.isRight && spatiallocation.isRight && addressid.isRight && rowguid.isRight && modifieddate.isRight)
      Right(AddressRowUnsaved(addressline1 = addressline1.toOption.get, addressline2 = addressline2.toOption.get, city = city.toOption.get, stateprovinceid = stateprovinceid.toOption.get, postalcode = postalcode.toOption.get, spatiallocation = spatiallocation.toOption.get, addressid = addressid.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](addressline1, addressline2, city, stateprovinceid, postalcode, spatiallocation, addressid, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[AddressRowUnsaved] = new JsonEncoder[AddressRowUnsaved] {
    override def unsafeEncode(a: AddressRowUnsaved, indent: Option[Int], out: Write): Unit = {
      out.write("{")
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
      out.write(""""addressid":""")
      Defaulted.jsonEncoder(AddressId.jsonEncoder).unsafeEncode(a.addressid, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      Defaulted.jsonEncoder(TypoUUID.jsonEncoder).unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      Defaulted.jsonEncoder(TypoLocalDateTime.jsonEncoder).unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[AddressRowUnsaved] = Text.instance[AddressRowUnsaved]{ (row, sb) =>
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
    Defaulted.text(AddressId.text).unsafeEncode(row.addressid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
}
