/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentitycontact

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.person.contacttype.ContacttypeId
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** This class corresponds to a row in table `person.businessentitycontact` which has not been persisted yet */
case class BusinessentitycontactRowUnsaved(
  /** Primary key. Foreign key to BusinessEntity.BusinessEntityID.
      Points to [[businessentity.BusinessentityRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Primary key. Foreign key to Person.BusinessEntityID.
      Points to [[person.PersonRow.businessentityid]] */
  personid: BusinessentityId,
  /** Primary key.  Foreign key to ContactType.ContactTypeID.
      Points to [[contacttype.ContacttypeRow.contacttypeid]] */
  contacttypeid: ContacttypeId,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): BusinessentitycontactRow =
    BusinessentitycontactRow(
      businessentityid = businessentityid,
      personid = personid,
      contacttypeid = contacttypeid,
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
object BusinessentitycontactRowUnsaved {
  implicit lazy val jsonDecoder: JsonDecoder[BusinessentitycontactRowUnsaved] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val personid = jsonObj.get("personid").toRight("Missing field 'personid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val contacttypeid = jsonObj.get("contacttypeid").toRight("Missing field 'contacttypeid'").flatMap(_.as(ContacttypeId.jsonDecoder))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(Defaulted.jsonDecoder(TypoUUID.jsonDecoder)))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(Defaulted.jsonDecoder(TypoLocalDateTime.jsonDecoder)))
    if (businessentityid.isRight && personid.isRight && contacttypeid.isRight && rowguid.isRight && modifieddate.isRight)
      Right(BusinessentitycontactRowUnsaved(businessentityid = businessentityid.toOption.get, personid = personid.toOption.get, contacttypeid = contacttypeid.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](businessentityid, personid, contacttypeid, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[BusinessentitycontactRowUnsaved] = new JsonEncoder[BusinessentitycontactRowUnsaved] {
    override def unsafeEncode(a: BusinessentitycontactRowUnsaved, indent: Option[Int], out: Write): Unit = {
      out.write("{")
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
      Defaulted.jsonEncoder(TypoUUID.jsonEncoder).unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      Defaulted.jsonEncoder(TypoLocalDateTime.jsonEncoder).unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[BusinessentitycontactRowUnsaved] = Text.instance[BusinessentitycontactRowUnsaved]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    BusinessentityId.text.unsafeEncode(row.personid, sb)
    sb.append(Text.DELIMETER)
    ContacttypeId.text.unsafeEncode(row.contacttypeid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
}
