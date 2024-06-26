/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package at

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.addresstype.AddresstypeId
import adventureworks.public.Name
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.Structure.Relation

trait AtViewFields {
  def id: Field[AddresstypeId, AtViewRow]
  def addresstypeid: Field[AddresstypeId, AtViewRow]
  def name: Field[Name, AtViewRow]
  def rowguid: Field[TypoUUID, AtViewRow]
  def modifieddate: Field[TypoLocalDateTime, AtViewRow]
}

object AtViewFields {
  lazy val structure: Relation[AtViewFields, AtViewRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[AtViewFields, AtViewRow] {
  
    override lazy val fields: AtViewFields = new AtViewFields {
      override def id = Field[AddresstypeId, AtViewRow](_path, "id", None, None, x => x.id, (row, value) => row.copy(id = value))
      override def addresstypeid = Field[AddresstypeId, AtViewRow](_path, "addresstypeid", None, None, x => x.addresstypeid, (row, value) => row.copy(addresstypeid = value))
      override def name = Field[Name, AtViewRow](_path, "name", None, None, x => x.name, (row, value) => row.copy(name = value))
      override def rowguid = Field[TypoUUID, AtViewRow](_path, "rowguid", None, None, x => x.rowguid, (row, value) => row.copy(rowguid = value))
      override def modifieddate = Field[TypoLocalDateTime, AtViewRow](_path, "modifieddate", Some("text"), None, x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, AtViewRow]] =
      List[FieldLikeNoHkt[?, AtViewRow]](fields.id, fields.addresstypeid, fields.name, fields.rowguid, fields.modifieddate)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
