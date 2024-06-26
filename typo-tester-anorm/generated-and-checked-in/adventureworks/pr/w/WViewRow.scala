/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package w

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.production.product.ProductId
import adventureworks.production.scrapreason.ScrapreasonId
import adventureworks.production.workorder.WorkorderId
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

/** View: pr.w */
case class WViewRow(
  /** Points to [[production.workorder.WorkorderRow.workorderid]] */
  id: WorkorderId,
  /** Points to [[production.workorder.WorkorderRow.workorderid]] */
  workorderid: WorkorderId,
  /** Points to [[production.workorder.WorkorderRow.productid]] */
  productid: ProductId,
  /** Points to [[production.workorder.WorkorderRow.orderqty]] */
  orderqty: Int,
  /** Points to [[production.workorder.WorkorderRow.scrappedqty]] */
  scrappedqty: TypoShort,
  /** Points to [[production.workorder.WorkorderRow.startdate]] */
  startdate: TypoLocalDateTime,
  /** Points to [[production.workorder.WorkorderRow.enddate]] */
  enddate: Option[TypoLocalDateTime],
  /** Points to [[production.workorder.WorkorderRow.duedate]] */
  duedate: TypoLocalDateTime,
  /** Points to [[production.workorder.WorkorderRow.scrapreasonid]] */
  scrapreasonid: Option[ScrapreasonId],
  /** Points to [[production.workorder.WorkorderRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object WViewRow {
  implicit lazy val reads: Reads[WViewRow] = Reads[WViewRow](json => JsResult.fromTry(
      Try(
        WViewRow(
          id = json.\("id").as(WorkorderId.reads),
          workorderid = json.\("workorderid").as(WorkorderId.reads),
          productid = json.\("productid").as(ProductId.reads),
          orderqty = json.\("orderqty").as(Reads.IntReads),
          scrappedqty = json.\("scrappedqty").as(TypoShort.reads),
          startdate = json.\("startdate").as(TypoLocalDateTime.reads),
          enddate = json.\("enddate").toOption.map(_.as(TypoLocalDateTime.reads)),
          duedate = json.\("duedate").as(TypoLocalDateTime.reads),
          scrapreasonid = json.\("scrapreasonid").toOption.map(_.as(ScrapreasonId.reads)),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[WViewRow] = RowParser[WViewRow] { row =>
    Success(
      WViewRow(
        id = row(idx + 0)(WorkorderId.column),
        workorderid = row(idx + 1)(WorkorderId.column),
        productid = row(idx + 2)(ProductId.column),
        orderqty = row(idx + 3)(Column.columnToInt),
        scrappedqty = row(idx + 4)(TypoShort.column),
        startdate = row(idx + 5)(TypoLocalDateTime.column),
        enddate = row(idx + 6)(Column.columnToOption(TypoLocalDateTime.column)),
        duedate = row(idx + 7)(TypoLocalDateTime.column),
        scrapreasonid = row(idx + 8)(Column.columnToOption(ScrapreasonId.column)),
        modifieddate = row(idx + 9)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[WViewRow] = OWrites[WViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> WorkorderId.writes.writes(o.id),
      "workorderid" -> WorkorderId.writes.writes(o.workorderid),
      "productid" -> ProductId.writes.writes(o.productid),
      "orderqty" -> Writes.IntWrites.writes(o.orderqty),
      "scrappedqty" -> TypoShort.writes.writes(o.scrappedqty),
      "startdate" -> TypoLocalDateTime.writes.writes(o.startdate),
      "enddate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.enddate),
      "duedate" -> TypoLocalDateTime.writes.writes(o.duedate),
      "scrapreasonid" -> Writes.OptionWrites(ScrapreasonId.writes).writes(o.scrapreasonid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}
