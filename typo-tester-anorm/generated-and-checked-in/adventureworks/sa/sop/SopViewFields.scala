/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sop

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import adventureworks.sales.specialoffer.SpecialofferId
import java.util.UUID
import typo.dsl.SqlExpr.Field

trait SopViewFields[Row] {
  val id: Field[SpecialofferId, Row]
  val specialofferid: Field[SpecialofferId, Row]
  val productid: Field[ProductId, Row]
  val rowguid: Field[UUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}
object SopViewFields extends SopViewStructure[SopViewRow](None, identity, (_, x) => x)
