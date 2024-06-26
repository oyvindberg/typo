/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentitycontact

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityFields
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.person.businessentity.BusinessentityRow
import adventureworks.person.contacttype.ContacttypeFields
import adventureworks.person.contacttype.ContacttypeId
import adventureworks.person.contacttype.ContacttypeRow
import adventureworks.person.person.PersonFields
import adventureworks.person.person.PersonRow
import typo.dsl.ForeignKey
import typo.dsl.Path
import typo.dsl.Required
import typo.dsl.SqlExpr
import typo.dsl.SqlExpr.CompositeIn
import typo.dsl.SqlExpr.CompositeIn.TuplePart
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait BusinessentitycontactFields {
  def businessentityid: IdField[BusinessentityId, BusinessentitycontactRow]
  def personid: IdField[BusinessentityId, BusinessentitycontactRow]
  def contacttypeid: IdField[ContacttypeId, BusinessentitycontactRow]
  def rowguid: Field[TypoUUID, BusinessentitycontactRow]
  def modifieddate: Field[TypoLocalDateTime, BusinessentitycontactRow]
  def fkBusinessentity: ForeignKey[BusinessentityFields, BusinessentityRow] =
    ForeignKey[BusinessentityFields, BusinessentityRow]("person.FK_BusinessEntityContact_BusinessEntity_BusinessEntityID", Nil)
      .withColumnPair(businessentityid, _.businessentityid)
  def fkContacttype: ForeignKey[ContacttypeFields, ContacttypeRow] =
    ForeignKey[ContacttypeFields, ContacttypeRow]("person.FK_BusinessEntityContact_ContactType_ContactTypeID", Nil)
      .withColumnPair(contacttypeid, _.contacttypeid)
  def fkPerson: ForeignKey[PersonFields, PersonRow] =
    ForeignKey[PersonFields, PersonRow]("person.FK_BusinessEntityContact_Person_PersonID", Nil)
      .withColumnPair(personid, _.businessentityid)
  def compositeIdIs(compositeId: BusinessentitycontactId): SqlExpr[Boolean, Required] =
    businessentityid.isEqual(compositeId.businessentityid).and(personid.isEqual(compositeId.personid)).and(contacttypeid.isEqual(compositeId.contacttypeid))
  def compositeIdIn(compositeIds: Array[BusinessentitycontactId]): SqlExpr[Boolean, Required] =
    new CompositeIn(compositeIds)(TuplePart(businessentityid)(_.businessentityid), TuplePart(personid)(_.personid), TuplePart(contacttypeid)(_.contacttypeid))
  
}

object BusinessentitycontactFields {
  lazy val structure: Relation[BusinessentitycontactFields, BusinessentitycontactRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[BusinessentitycontactFields, BusinessentitycontactRow] {
  
    override lazy val fields: BusinessentitycontactFields = new BusinessentitycontactFields {
      override def businessentityid = IdField[BusinessentityId, BusinessentitycontactRow](_path, "businessentityid", None, Some("int4"), x => x.businessentityid, (row, value) => row.copy(businessentityid = value))
      override def personid = IdField[BusinessentityId, BusinessentitycontactRow](_path, "personid", None, Some("int4"), x => x.personid, (row, value) => row.copy(personid = value))
      override def contacttypeid = IdField[ContacttypeId, BusinessentitycontactRow](_path, "contacttypeid", None, Some("int4"), x => x.contacttypeid, (row, value) => row.copy(contacttypeid = value))
      override def rowguid = Field[TypoUUID, BusinessentitycontactRow](_path, "rowguid", None, Some("uuid"), x => x.rowguid, (row, value) => row.copy(rowguid = value))
      override def modifieddate = Field[TypoLocalDateTime, BusinessentitycontactRow](_path, "modifieddate", Some("text"), Some("timestamp"), x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, BusinessentitycontactRow]] =
      List[FieldLikeNoHkt[?, BusinessentitycontactRow]](fields.businessentityid, fields.personid, fields.contacttypeid, fields.rowguid, fields.modifieddate)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
