/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salestaxrate

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.stateprovince.StateprovinceId
import adventureworks.public.Name
import java.util.UUID
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

/** This class corresponds to a row in table `sales.salestaxrate` which has not been persisted yet */
case class SalestaxrateRowUnsaved(
  /** State, province, or country/region the sales tax applies to.
      Points to [[person.stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** 1 = Tax applied to retail transactions, 2 = Tax applied to wholesale transactions, 3 = Tax applied to all sales (retail and wholesale) transactions. */
  taxtype: Int,
  /** Tax rate description. */
  name: Name,
  /** Default: nextval('sales.salestaxrate_salestaxrateid_seq'::regclass)
      Primary key for SalesTaxRate records. */
  salestaxrateid: Defaulted[SalestaxrateId] = Defaulted.UseDefault,
  /** Default: 0.00
      Tax rate amount. */
  taxrate: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[UUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(salestaxrateidDefault: => SalestaxrateId, taxrateDefault: => BigDecimal, rowguidDefault: => UUID, modifieddateDefault: => TypoLocalDateTime): SalestaxrateRow =
    SalestaxrateRow(
      stateprovinceid = stateprovinceid,
      taxtype = taxtype,
      name = name,
      salestaxrateid = salestaxrateid match {
                         case Defaulted.UseDefault => salestaxrateidDefault
                         case Defaulted.Provided(value) => value
                       },
      taxrate = taxrate match {
                  case Defaulted.UseDefault => taxrateDefault
                  case Defaulted.Provided(value) => value
                },
      rowguid = rowguid match {
                  case Defaulted.UseDefault => rowguidDefault
                  case Defaulted.Provided(value) => value
                },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object SalestaxrateRowUnsaved {
  implicit lazy val reads: Reads[SalestaxrateRowUnsaved] = Reads[SalestaxrateRowUnsaved](json => JsResult.fromTry(
      Try(
        SalestaxrateRowUnsaved(
          stateprovinceid = json.\("stateprovinceid").as(StateprovinceId.reads),
          taxtype = json.\("taxtype").as(Reads.IntReads),
          name = json.\("name").as(Name.reads),
          salestaxrateid = json.\("salestaxrateid").as(Defaulted.reads(SalestaxrateId.reads)),
          taxrate = json.\("taxrate").as(Defaulted.reads(Reads.bigDecReads)),
          rowguid = json.\("rowguid").as(Defaulted.reads(Reads.uuidReads)),
          modifieddate = json.\("modifieddate").as(Defaulted.reads(TypoLocalDateTime.reads))
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[SalestaxrateRowUnsaved] = OWrites[SalestaxrateRowUnsaved](o =>
    new JsObject(ListMap[String, JsValue](
      "stateprovinceid" -> StateprovinceId.writes.writes(o.stateprovinceid),
      "taxtype" -> Writes.IntWrites.writes(o.taxtype),
      "name" -> Name.writes.writes(o.name),
      "salestaxrateid" -> Defaulted.writes(SalestaxrateId.writes).writes(o.salestaxrateid),
      "taxrate" -> Defaulted.writes(Writes.BigDecimalWrites).writes(o.taxrate),
      "rowguid" -> Defaulted.writes(Writes.UuidWrites).writes(o.rowguid),
      "modifieddate" -> Defaulted.writes(TypoLocalDateTime.writes).writes(o.modifieddate)
    ))
  )
}