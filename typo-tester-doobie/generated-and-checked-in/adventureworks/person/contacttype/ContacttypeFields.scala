/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package contacttype

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait ContacttypeFields {
  def contacttypeid: IdField[ContacttypeId, ContacttypeRow]
  def name: Field[Name, ContacttypeRow]
  def modifieddate: Field[TypoLocalDateTime, ContacttypeRow]
}

object ContacttypeFields {
  lazy val structure: Relation[ContacttypeFields, ContacttypeRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[ContacttypeFields, ContacttypeRow] {
  
    override lazy val fields: ContacttypeFields = new ContacttypeFields {
      override def contacttypeid = IdField[ContacttypeId, ContacttypeRow](_path, "contacttypeid", None, Some("int4"), x => x.contacttypeid, (row, value) => row.copy(contacttypeid = value))
      override def name = Field[Name, ContacttypeRow](_path, "name", None, Some("varchar"), x => x.name, (row, value) => row.copy(name = value))
      override def modifieddate = Field[TypoLocalDateTime, ContacttypeRow](_path, "modifieddate", Some("text"), Some("timestamp"), x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, ContacttypeRow]] =
      List[FieldLikeNoHkt[?, ContacttypeRow]](fields.contacttypeid, fields.name, fields.modifieddate)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
