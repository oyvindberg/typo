/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package phonenumbertype

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait PhonenumbertypeFields[Row] {
  val phonenumbertypeid: IdField[PhonenumbertypeId, Row]
  val name: Field[Name, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object PhonenumbertypeFields {
  val structure: Relation[PhonenumbertypeFields, PhonenumbertypeRow, PhonenumbertypeRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => PhonenumbertypeRow, val merge: (Row, PhonenumbertypeRow) => Row)
    extends Relation[PhonenumbertypeFields, PhonenumbertypeRow, Row] { 
  
    override val fields: PhonenumbertypeFields[Row] = new PhonenumbertypeFields[Row] {
      override val phonenumbertypeid = new IdField[PhonenumbertypeId, Row](prefix, "phonenumbertypeid", None, Some("int4"))(x => extract(x).phonenumbertypeid, (row, value) => merge(row, extract(row).copy(phonenumbertypeid = value)))
      override val name = new Field[Name, Row](prefix, "name", None, Some("varchar"))(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.phonenumbertypeid, fields.name, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => PhonenumbertypeRow, merge: (NewRow, PhonenumbertypeRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
