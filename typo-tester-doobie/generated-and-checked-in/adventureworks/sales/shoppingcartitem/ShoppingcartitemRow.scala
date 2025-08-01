/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package shoppingcartitem

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Table: sales.shoppingcartitem
    Contains online customer orders until the order is submitted or cancelled.
    Primary key: shoppingcartitemid */
case class ShoppingcartitemRow(
  /** Primary key for ShoppingCartItem records.
      Default: nextval('sales.shoppingcartitem_shoppingcartitemid_seq'::regclass) */
  shoppingcartitemid: ShoppingcartitemId,
  /** Shopping cart identification number. */
  shoppingcartid: /* max 50 chars */ String,
  /** Product quantity ordered.
      Default: 1
      Constraint CK_ShoppingCartItem_Quantity affecting columns quantity: ((quantity >= 1)) */
  quantity: Int,
  /** Product ordered. Foreign key to Product.ProductID.
      Points to [[production.product.ProductRow.productid]] */
  productid: ProductId,
  /** Date the time the record was created.
      Default: now() */
  datecreated: TypoLocalDateTime,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = shoppingcartitemid
   def toUnsavedRow(shoppingcartitemid: Defaulted[ShoppingcartitemId], quantity: Defaulted[Int] = Defaulted.Provided(this.quantity), datecreated: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.datecreated), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): ShoppingcartitemRowUnsaved =
     ShoppingcartitemRowUnsaved(shoppingcartid, productid, shoppingcartitemid, quantity, datecreated, modifieddate)
 }

object ShoppingcartitemRow {
  implicit lazy val decoder: Decoder[ShoppingcartitemRow] = Decoder.forProduct6[ShoppingcartitemRow, ShoppingcartitemId, /* max 50 chars */ String, Int, ProductId, TypoLocalDateTime, TypoLocalDateTime]("shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate")(ShoppingcartitemRow.apply)(ShoppingcartitemId.decoder, Decoder.decodeString, Decoder.decodeInt, ProductId.decoder, TypoLocalDateTime.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[ShoppingcartitemRow] = Encoder.forProduct6[ShoppingcartitemRow, ShoppingcartitemId, /* max 50 chars */ String, Int, ProductId, TypoLocalDateTime, TypoLocalDateTime]("shoppingcartitemid", "shoppingcartid", "quantity", "productid", "datecreated", "modifieddate")(x => (x.shoppingcartitemid, x.shoppingcartid, x.quantity, x.productid, x.datecreated, x.modifieddate))(ShoppingcartitemId.encoder, Encoder.encodeString, Encoder.encodeInt, ProductId.encoder, TypoLocalDateTime.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[ShoppingcartitemRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(ShoppingcartitemId.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(ProductId.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    ShoppingcartitemRow(
      shoppingcartitemid = arr(0).asInstanceOf[ShoppingcartitemId],
          shoppingcartid = arr(1).asInstanceOf[/* max 50 chars */ String],
          quantity = arr(2).asInstanceOf[Int],
          productid = arr(3).asInstanceOf[ProductId],
          datecreated = arr(4).asInstanceOf[TypoLocalDateTime],
          modifieddate = arr(5).asInstanceOf[TypoLocalDateTime]
    )
  }
  implicit lazy val text: Text[ShoppingcartitemRow] = Text.instance[ShoppingcartitemRow]{ (row, sb) =>
    ShoppingcartitemId.text.unsafeEncode(row.shoppingcartitemid, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.shoppingcartid, sb)
    sb.append(Text.DELIMETER)
    Text.intInstance.unsafeEncode(row.quantity, sb)
    sb.append(Text.DELIMETER)
    ProductId.text.unsafeEncode(row.productid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.datecreated, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[ShoppingcartitemRow] = new Write.Composite[ShoppingcartitemRow](
    List(new Write.Single(ShoppingcartitemId.put),
         new Write.Single(Meta.StringMeta.put),
         new Write.Single(Meta.IntMeta.put),
         new Write.Single(ProductId.put),
         new Write.Single(TypoLocalDateTime.put),
         new Write.Single(TypoLocalDateTime.put)),
    a => List(a.shoppingcartitemid, a.shoppingcartid, a.quantity, a.productid, a.datecreated, a.modifieddate)
  )
}
