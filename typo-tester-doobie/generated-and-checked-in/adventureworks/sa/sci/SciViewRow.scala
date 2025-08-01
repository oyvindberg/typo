/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sci

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import adventureworks.sales.shoppingcartitem.ShoppingcartitemId
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** View: sa.sci */
case class SciViewRow(
  /** Points to [[sales.shoppingcartitem.ShoppingcartitemRow.shoppingcartitemid]] */
  id: ShoppingcartitemId,
  /** Points to [[sales.shoppingcartitem.ShoppingcartitemRow.shoppingcartitemid]] */
  shoppingcartitemid: ShoppingcartitemId,
  /** Points to [[sales.shoppingcartitem.ShoppingcartitemRow.shoppingcartid]] */
  shoppingcartid: /* max 50 chars */ String,
  /** Points to [[sales.shoppingcartitem.ShoppingcartitemRow.quantity]] */
  quantity: Int,
  /** Points to [[sales.shoppingcartitem.ShoppingcartitemRow.productid]] */
  productid: ProductId,
  /** Points to [[sales.shoppingcartitem.ShoppingcartitemRow.datecreated]] */
  datecreated: TypoLocalDateTime,
  /** Points to [[sales.shoppingcartitem.ShoppingcartitemRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object SciViewRow {
  implicit lazy val decoder: Decoder[SciViewRow] = Decoder.forProduct7[SciViewRow, ShoppingcartitemId, ShoppingcartitemId, /* max 50 chars */ String, Int, ProductId, TypoLocalDateTime, TypoLocalDateTime]("id", "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate")(SciViewRow.apply)(ShoppingcartitemId.decoder, ShoppingcartitemId.decoder, Decoder.decodeString, Decoder.decodeInt, ProductId.decoder, TypoLocalDateTime.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[SciViewRow] = Encoder.forProduct7[SciViewRow, ShoppingcartitemId, ShoppingcartitemId, /* max 50 chars */ String, Int, ProductId, TypoLocalDateTime, TypoLocalDateTime]("id", "shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate")(x => (x.id, x.shoppingcartitemid, x.shoppingcartid, x.quantity, x.productid, x.datecreated, x.modifieddate))(ShoppingcartitemId.encoder, ShoppingcartitemId.encoder, Encoder.encodeString, Encoder.encodeInt, ProductId.encoder, TypoLocalDateTime.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[SciViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(ShoppingcartitemId.get).asInstanceOf[Read[Any]],
      new Read.Single(ShoppingcartitemId.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(ProductId.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    SciViewRow(
      id = arr(0).asInstanceOf[ShoppingcartitemId],
          shoppingcartitemid = arr(1).asInstanceOf[ShoppingcartitemId],
          shoppingcartid = arr(2).asInstanceOf[/* max 50 chars */ String],
          quantity = arr(3).asInstanceOf[Int],
          productid = arr(4).asInstanceOf[ProductId],
          datecreated = arr(5).asInstanceOf[TypoLocalDateTime],
          modifieddate = arr(6).asInstanceOf[TypoLocalDateTime]
    )
  }
}
