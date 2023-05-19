/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package shoppingcartitem

import adventureworks.production.product.ProductId
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.time.LocalDateTime

case class ShoppingcartitemRow(
  /** Primary key for ShoppingCartItem records. */
  shoppingcartitemid: ShoppingcartitemId,
  /** Shopping cart identification number. */
  shoppingcartid: String,
  /** Product quantity ordered. */
  quantity: Int,
  /** Product ordered. Foreign key to Product.ProductID.
      Points to [[production.product.ProductRow.productid]] */
  productid: ProductId,
  /** Date the time the record was created. */
  datecreated: LocalDateTime,
  modifieddate: LocalDateTime
)

object ShoppingcartitemRow {
  implicit val decoder: Decoder[ShoppingcartitemRow] =
    (c: HCursor) =>
      for {
        shoppingcartitemid <- c.downField("shoppingcartitemid").as[ShoppingcartitemId]
        shoppingcartid <- c.downField("shoppingcartid").as[String]
        quantity <- c.downField("quantity").as[Int]
        productid <- c.downField("productid").as[ProductId]
        datecreated <- c.downField("datecreated").as[LocalDateTime]
        modifieddate <- c.downField("modifieddate").as[LocalDateTime]
      } yield ShoppingcartitemRow(shoppingcartitemid, shoppingcartid, quantity, productid, datecreated, modifieddate)
  implicit val encoder: Encoder[ShoppingcartitemRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "shoppingcartitemid" := row.shoppingcartitemid,
        "shoppingcartid" := row.shoppingcartid,
        "quantity" := row.quantity,
        "productid" := row.productid,
        "datecreated" := row.datecreated,
        "modifieddate" := row.modifieddate
      )}
}