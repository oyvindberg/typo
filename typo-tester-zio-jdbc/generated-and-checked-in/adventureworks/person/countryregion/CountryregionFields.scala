/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package countryregion

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait CountryregionFields[Row] {
  val countryregioncode: IdField[CountryregionId, Row]
  val name: Field[Name, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object CountryregionFields {
  val structure: Relation[CountryregionFields, CountryregionRow, CountryregionRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => CountryregionRow, val merge: (Row, CountryregionRow) => Row)
    extends Relation[CountryregionFields, CountryregionRow, Row] { 
  
    override val fields: CountryregionFields[Row] = new CountryregionFields[Row] {
      override val countryregioncode = new IdField[CountryregionId, Row](prefix, "countryregioncode", None, None)(x => extract(x).countryregioncode, (row, value) => merge(row, extract(row).copy(countryregioncode = value)))
      override val name = new Field[Name, Row](prefix, "name", None, Some("varchar"))(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.countryregioncode, fields.name, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => CountryregionRow, merge: (NewRow, CountryregionRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
