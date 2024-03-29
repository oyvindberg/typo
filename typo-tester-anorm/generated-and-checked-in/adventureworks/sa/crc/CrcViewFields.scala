/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package crc

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.countryregion.CountryregionId
import adventureworks.sales.currency.CurrencyId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.Structure.Relation

trait CrcViewFields[Row] {
  val countryregioncode: Field[CountryregionId, Row]
  val currencycode: Field[CurrencyId, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object CrcViewFields {
  val structure: Relation[CrcViewFields, CrcViewRow, CrcViewRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => CrcViewRow, val merge: (Row, CrcViewRow) => Row)
    extends Relation[CrcViewFields, CrcViewRow, Row] { 
  
    override val fields: CrcViewFields[Row] = new CrcViewFields[Row] {
      override val countryregioncode = new Field[CountryregionId, Row](prefix, "countryregioncode", None, None)(x => extract(x).countryregioncode, (row, value) => merge(row, extract(row).copy(countryregioncode = value)))
      override val currencycode = new Field[CurrencyId, Row](prefix, "currencycode", None, None)(x => extract(x).currencycode, (row, value) => merge(row, extract(row).copy(currencycode = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), None)(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.countryregioncode, fields.currencycode, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => CrcViewRow, merge: (NewRow, CrcViewRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
