/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currencyrate

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.sales.currency.CurrencyId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait CurrencyrateFields[Row] {
  val currencyrateid: IdField[CurrencyrateId, Row]
  val currencyratedate: Field[TypoLocalDateTime, Row]
  val fromcurrencycode: Field[CurrencyId, Row]
  val tocurrencycode: Field[CurrencyId, Row]
  val averagerate: Field[BigDecimal, Row]
  val endofdayrate: Field[BigDecimal, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object CurrencyrateFields {
  val structure: Relation[CurrencyrateFields, CurrencyrateRow, CurrencyrateRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => CurrencyrateRow, val merge: (Row, CurrencyrateRow) => Row)
    extends Relation[CurrencyrateFields, CurrencyrateRow, Row] { 
  
    override val fields: CurrencyrateFields[Row] = new CurrencyrateFields[Row] {
      override val currencyrateid = new IdField[CurrencyrateId, Row](prefix, "currencyrateid", None, Some("int4"))(x => extract(x).currencyrateid, (row, value) => merge(row, extract(row).copy(currencyrateid = value)))
      override val currencyratedate = new Field[TypoLocalDateTime, Row](prefix, "currencyratedate", Some("text"), Some("timestamp"))(x => extract(x).currencyratedate, (row, value) => merge(row, extract(row).copy(currencyratedate = value)))
      override val fromcurrencycode = new Field[CurrencyId, Row](prefix, "fromcurrencycode", None, Some("bpchar"))(x => extract(x).fromcurrencycode, (row, value) => merge(row, extract(row).copy(fromcurrencycode = value)))
      override val tocurrencycode = new Field[CurrencyId, Row](prefix, "tocurrencycode", None, Some("bpchar"))(x => extract(x).tocurrencycode, (row, value) => merge(row, extract(row).copy(tocurrencycode = value)))
      override val averagerate = new Field[BigDecimal, Row](prefix, "averagerate", None, Some("numeric"))(x => extract(x).averagerate, (row, value) => merge(row, extract(row).copy(averagerate = value)))
      override val endofdayrate = new Field[BigDecimal, Row](prefix, "endofdayrate", None, Some("numeric"))(x => extract(x).endofdayrate, (row, value) => merge(row, extract(row).copy(endofdayrate = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.currencyrateid, fields.currencyratedate, fields.fromcurrencycode, fields.tocurrencycode, fields.averagerate, fields.endofdayrate, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => CurrencyrateRow, merge: (NewRow, CurrencyrateRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}