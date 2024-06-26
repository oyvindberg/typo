/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package plph

import adventureworks.customtypes.TypoLocalDateTime
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

/** View: pr.plph */
case class PlphViewRow(
  /** Points to [[production.productlistpricehistory.ProductlistpricehistoryRow.productid]] */
  id: ProductId,
  /** Points to [[production.productlistpricehistory.ProductlistpricehistoryRow.productid]] */
  productid: ProductId,
  /** Points to [[production.productlistpricehistory.ProductlistpricehistoryRow.startdate]] */
  startdate: TypoLocalDateTime,
  /** Points to [[production.productlistpricehistory.ProductlistpricehistoryRow.enddate]] */
  enddate: Option[TypoLocalDateTime],
  /** Points to [[production.productlistpricehistory.ProductlistpricehistoryRow.listprice]] */
  listprice: BigDecimal,
  /** Points to [[production.productlistpricehistory.ProductlistpricehistoryRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PlphViewRow {
  implicit lazy val reads: Reads[PlphViewRow] = Reads[PlphViewRow](json => JsResult.fromTry(
      Try(
        PlphViewRow(
          id = json.\("id").as(ProductId.reads),
          productid = json.\("productid").as(ProductId.reads),
          startdate = json.\("startdate").as(TypoLocalDateTime.reads),
          enddate = json.\("enddate").toOption.map(_.as(TypoLocalDateTime.reads)),
          listprice = json.\("listprice").as(Reads.bigDecReads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PlphViewRow] = RowParser[PlphViewRow] { row =>
    Success(
      PlphViewRow(
        id = row(idx + 0)(ProductId.column),
        productid = row(idx + 1)(ProductId.column),
        startdate = row(idx + 2)(TypoLocalDateTime.column),
        enddate = row(idx + 3)(Column.columnToOption(TypoLocalDateTime.column)),
        listprice = row(idx + 4)(Column.columnToScalaBigDecimal),
        modifieddate = row(idx + 5)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[PlphViewRow] = OWrites[PlphViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> ProductId.writes.writes(o.id),
      "productid" -> ProductId.writes.writes(o.productid),
      "startdate" -> TypoLocalDateTime.writes.writes(o.startdate),
      "enddate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.enddate),
      "listprice" -> Writes.BigDecimalWrites.writes(o.listprice),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}
