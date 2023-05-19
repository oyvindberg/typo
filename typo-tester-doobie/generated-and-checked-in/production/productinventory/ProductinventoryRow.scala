/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productinventory

import adventureworks.production.location.LocationId
import adventureworks.production.product.ProductId
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.time.LocalDateTime
import java.util.UUID

case class ProductinventoryRow(
  /** Product identification number. Foreign key to Product.ProductID.
      Points to [[product.ProductRow.productid]] */
  productid: ProductId,
  /** Inventory location identification number. Foreign key to Location.LocationID.
      Points to [[location.LocationRow.locationid]] */
  locationid: LocationId,
  /** Storage compartment within an inventory location. */
  shelf: String,
  /** Storage container on a shelf in an inventory location. */
  bin: Int,
  /** Quantity of products in the inventory location. */
  quantity: Int,
  rowguid: UUID,
  modifieddate: LocalDateTime
){
   val compositeId: ProductinventoryId = ProductinventoryId(productid, locationid)
 }

object ProductinventoryRow {
  implicit val decoder: Decoder[ProductinventoryRow] =
    (c: HCursor) =>
      for {
        productid <- c.downField("productid").as[ProductId]
        locationid <- c.downField("locationid").as[LocationId]
        shelf <- c.downField("shelf").as[String]
        bin <- c.downField("bin").as[Int]
        quantity <- c.downField("quantity").as[Int]
        rowguid <- c.downField("rowguid").as[UUID]
        modifieddate <- c.downField("modifieddate").as[LocalDateTime]
      } yield ProductinventoryRow(productid, locationid, shelf, bin, quantity, rowguid, modifieddate)
  implicit val encoder: Encoder[ProductinventoryRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "productid" := row.productid,
        "locationid" := row.locationid,
        "shelf" := row.shelf,
        "bin" := row.bin,
        "quantity" := row.quantity,
        "rowguid" := row.rowguid,
        "modifieddate" := row.modifieddate
      )}
}