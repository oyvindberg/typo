/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package shoppingcartitem

import adventureworks.TypoLocalDateTime
import adventureworks.production.product.ProductId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.IdField

trait ShoppingcartitemFields[Row] {
  val shoppingcartitemid: IdField[ShoppingcartitemId, Row]
  val shoppingcartid: Field[/* max 50 chars */ String, Row]
  val quantity: Field[Int, Row]
  val productid: Field[ProductId, Row]
  val datecreated: Field[TypoLocalDateTime, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}
object ShoppingcartitemFields extends ShoppingcartitemStructure[ShoppingcartitemRow](None, identity, (_, x) => x)
