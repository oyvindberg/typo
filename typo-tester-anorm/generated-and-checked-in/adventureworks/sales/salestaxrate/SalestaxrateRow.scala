/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salestaxrate

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.stateprovince.StateprovinceId
import adventureworks.public.Name
import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class SalestaxrateRow(
  /** Primary key for SalesTaxRate records. */
  salestaxrateid: SalestaxrateId,
  /** State, province, or country/region the sales tax applies to.
      Points to [[person.stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** 1 = Tax applied to retail transactions, 2 = Tax applied to wholesale transactions, 3 = Tax applied to all sales (retail and wholesale) transactions.
      Constraint CK_SalesTaxRate_TaxType affecting columns "taxtype":  (((taxtype >= 1) AND (taxtype <= 3))) */
  taxtype: TypoShort,
  /** Tax rate amount. */
  taxrate: BigDecimal,
  /** Tax rate description. */
  name: Name,
  rowguid: TypoUUID,
  modifieddate: TypoLocalDateTime
)

object SalestaxrateRow {
  implicit lazy val reads: Reads[SalestaxrateRow] = Reads[SalestaxrateRow](json => JsResult.fromTry(
      Try(
        SalestaxrateRow(
          salestaxrateid = json.\("salestaxrateid").as(SalestaxrateId.reads),
          stateprovinceid = json.\("stateprovinceid").as(StateprovinceId.reads),
          taxtype = json.\("taxtype").as(TypoShort.reads),
          taxrate = json.\("taxrate").as(Reads.bigDecReads),
          name = json.\("name").as(Name.reads),
          rowguid = json.\("rowguid").as(TypoUUID.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[SalestaxrateRow] = RowParser[SalestaxrateRow] { row =>
    Success(
      SalestaxrateRow(
        salestaxrateid = row(idx + 0)(SalestaxrateId.column),
        stateprovinceid = row(idx + 1)(StateprovinceId.column),
        taxtype = row(idx + 2)(TypoShort.column),
        taxrate = row(idx + 3)(Column.columnToScalaBigDecimal),
        name = row(idx + 4)(Name.column),
        rowguid = row(idx + 5)(TypoUUID.column),
        modifieddate = row(idx + 6)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[SalestaxrateRow] = OWrites[SalestaxrateRow](o =>
    new JsObject(ListMap[String, JsValue](
      "salestaxrateid" -> SalestaxrateId.writes.writes(o.salestaxrateid),
      "stateprovinceid" -> StateprovinceId.writes.writes(o.stateprovinceid),
      "taxtype" -> TypoShort.writes.writes(o.taxtype),
      "taxrate" -> Writes.BigDecimalWrites.writes(o.taxrate),
      "name" -> Name.writes.writes(o.name),
      "rowguid" -> TypoUUID.writes.writes(o.rowguid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}