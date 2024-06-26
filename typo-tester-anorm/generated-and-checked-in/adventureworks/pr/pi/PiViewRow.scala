/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pi

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.production.location.LocationId
import adventureworks.production.product.ProductId
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

/** View: pr.pi */
case class PiViewRow(
  /** Points to [[production.productinventory.ProductinventoryRow.productid]] */
  id: ProductId,
  /** Points to [[production.productinventory.ProductinventoryRow.productid]] */
  productid: ProductId,
  /** Points to [[production.productinventory.ProductinventoryRow.locationid]] */
  locationid: LocationId,
  /** Points to [[production.productinventory.ProductinventoryRow.shelf]] */
  shelf: /* max 10 chars */ String,
  /** Points to [[production.productinventory.ProductinventoryRow.bin]] */
  bin: TypoShort,
  /** Points to [[production.productinventory.ProductinventoryRow.quantity]] */
  quantity: TypoShort,
  /** Points to [[production.productinventory.ProductinventoryRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[production.productinventory.ProductinventoryRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PiViewRow {
  implicit lazy val reads: Reads[PiViewRow] = Reads[PiViewRow](json => JsResult.fromTry(
      Try(
        PiViewRow(
          id = json.\("id").as(ProductId.reads),
          productid = json.\("productid").as(ProductId.reads),
          locationid = json.\("locationid").as(LocationId.reads),
          shelf = json.\("shelf").as(Reads.StringReads),
          bin = json.\("bin").as(TypoShort.reads),
          quantity = json.\("quantity").as(TypoShort.reads),
          rowguid = json.\("rowguid").as(TypoUUID.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PiViewRow] = RowParser[PiViewRow] { row =>
    Success(
      PiViewRow(
        id = row(idx + 0)(ProductId.column),
        productid = row(idx + 1)(ProductId.column),
        locationid = row(idx + 2)(LocationId.column),
        shelf = row(idx + 3)(Column.columnToString),
        bin = row(idx + 4)(TypoShort.column),
        quantity = row(idx + 5)(TypoShort.column),
        rowguid = row(idx + 6)(TypoUUID.column),
        modifieddate = row(idx + 7)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[PiViewRow] = OWrites[PiViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> ProductId.writes.writes(o.id),
      "productid" -> ProductId.writes.writes(o.productid),
      "locationid" -> LocationId.writes.writes(o.locationid),
      "shelf" -> Writes.StringWrites.writes(o.shelf),
      "bin" -> TypoShort.writes.writes(o.bin),
      "quantity" -> TypoShort.writes.writes(o.quantity),
      "rowguid" -> TypoUUID.writes.writes(o.rowguid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}
