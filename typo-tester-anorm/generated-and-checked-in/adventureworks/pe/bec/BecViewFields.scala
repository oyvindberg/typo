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
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.Structure.Relation

trait BecViewFields {
  def id: Field[BusinessentityId, BecViewRow]
  def businessentityid: Field[BusinessentityId, BecViewRow]
  def personid: Field[BusinessentityId, BecViewRow]
  def contacttypeid: Field[ContacttypeId, BecViewRow]
  def rowguid: Field[TypoUUID, BecViewRow]
  def modifieddate: Field[TypoLocalDateTime, BecViewRow]
}

object BecViewFields {
  lazy val structure: Relation[BecViewFields, BecViewRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[BecViewFields, BecViewRow] {
  
    override lazy val fields: BecViewFields = new BecViewFields {
      override def id = Field[BusinessentityId, BecViewRow](_path, "id", None, None, x => x.id, (row, value) => row.copy(id = value))
      override def businessentityid = Field[BusinessentityId, BecViewRow](_path, "businessentityid", None, None, x => x.businessentityid, (row, value) => row.copy(businessentityid = value))
      override def personid = Field[BusinessentityId, BecViewRow](_path, "personid", None, None, x => x.personid, (row, value) => row.copy(personid = value))
      override def contacttypeid = Field[ContacttypeId, BecViewRow](_path, "contacttypeid", None, None, x => x.contacttypeid, (row, value) => row.copy(contacttypeid = value))
      override def rowguid = Field[TypoUUID, BecViewRow](_path, "rowguid", None, None, x => x.rowguid, (row, value) => row.copy(rowguid = value))
      override def modifieddate = Field[TypoLocalDateTime, BecViewRow](_path, "modifieddate", Some("text"), None, x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, BecViewRow]] =
      List[FieldLikeNoHkt[?, BecViewRow]](fields.id, fields.businessentityid, fields.personid, fields.contacttypeid, fields.rowguid, fields.modifieddate)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
