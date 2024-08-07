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
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** Table: person.businessentitycontact
    Cross-reference table mapping stores, vendors, and employees to people
    Composite primary key: businessentityid, personid, contacttypeid */
case class BusinessentitycontactRow(
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
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val compositeId: BusinessentitycontactId = BusinessentitycontactId(businessentityid, personid, contacttypeid)
   val id = compositeId
   def toUnsavedRow(rowguid: Defaulted[TypoUUID] = Defaulted.Provided(this.rowguid), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): BusinessentitycontactRowUnsaved =
     BusinessentitycontactRowUnsaved(businessentityid, personid, contacttypeid, rowguid, modifieddate)
 }

object BusinessentitycontactRow {
  def apply(compositeId: BusinessentitycontactId, rowguid: TypoUUID, modifieddate: TypoLocalDateTime) =
    new BusinessentitycontactRow(compositeId.businessentityid, compositeId.personid, compositeId.contacttypeid, rowguid, modifieddate)
  implicit lazy val decoder: Decoder[BusinessentitycontactRow] = Decoder.forProduct5[BusinessentitycontactRow, BusinessentityId, BusinessentityId, ContacttypeId, TypoUUID, TypoLocalDateTime]("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate")(BusinessentitycontactRow.apply)(BusinessentityId.decoder, BusinessentityId.decoder, ContacttypeId.decoder, TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[BusinessentitycontactRow] = Encoder.forProduct5[BusinessentitycontactRow, BusinessentityId, BusinessentityId, ContacttypeId, TypoUUID, TypoLocalDateTime]("businessentityid", "personid", "contacttypeid", "rowguid", "modifieddate")(x => (x.businessentityid, x.personid, x.contacttypeid, x.rowguid, x.modifieddate))(BusinessentityId.encoder, BusinessentityId.encoder, ContacttypeId.encoder, TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[BusinessentitycontactRow] = new Read[BusinessentitycontactRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (BusinessentityId.get, Nullability.NoNulls),
      (ContacttypeId.get, Nullability.NoNulls),
      (TypoUUID.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => BusinessentitycontactRow(
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      personid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 1),
      contacttypeid = ContacttypeId.get.unsafeGetNonNullable(rs, i + 2),
      rowguid = TypoUUID.get.unsafeGetNonNullable(rs, i + 3),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 4)
    )
  )
  implicit lazy val text: Text[BusinessentitycontactRow] = Text.instance[BusinessentitycontactRow]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    BusinessentityId.text.unsafeEncode(row.personid, sb)
    sb.append(Text.DELIMETER)
    ContacttypeId.text.unsafeEncode(row.contacttypeid, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[BusinessentitycontactRow] = new Write[BusinessentitycontactRow](
    puts = List((BusinessentityId.put, Nullability.NoNulls),
                (BusinessentityId.put, Nullability.NoNulls),
                (ContacttypeId.put, Nullability.NoNulls),
                (TypoUUID.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.NoNulls)),
    toList = x => List(x.businessentityid, x.personid, x.contacttypeid, x.rowguid, x.modifieddate),
    unsafeSet = (rs, i, a) => {
                  BusinessentityId.put.unsafeSetNonNullable(rs, i + 0, a.businessentityid)
                  BusinessentityId.put.unsafeSetNonNullable(rs, i + 1, a.personid)
                  ContacttypeId.put.unsafeSetNonNullable(rs, i + 2, a.contacttypeid)
                  TypoUUID.put.unsafeSetNonNullable(rs, i + 3, a.rowguid)
                  TypoLocalDateTime.put.unsafeSetNonNullable(rs, i + 4, a.modifieddate)
                },
    unsafeUpdate = (ps, i, a) => {
                     BusinessentityId.put.unsafeUpdateNonNullable(ps, i + 0, a.businessentityid)
                     BusinessentityId.put.unsafeUpdateNonNullable(ps, i + 1, a.personid)
                     ContacttypeId.put.unsafeUpdateNonNullable(ps, i + 2, a.contacttypeid)
                     TypoUUID.put.unsafeUpdateNonNullable(ps, i + 3, a.rowguid)
                     TypoLocalDateTime.put.unsafeUpdateNonNullable(ps, i + 4, a.modifieddate)
                   }
  )
}
