/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package ppp

import adventureworks.TypoLocalDateTime
import adventureworks.production.product.ProductId
import adventureworks.production.productphoto.ProductphotoId
import adventureworks.public.Flag
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.OptField

trait PppViewFields[Row] {
  val productid: OptField[ProductId, Row]
  val productphotoid: OptField[ProductphotoId, Row]
  val primary: Field[Flag, Row]
  val modifieddate: OptField[TypoLocalDateTime, Row]
}
object PppViewFields extends PppViewStructure[PppViewRow](None, identity, (_, x) => x)
