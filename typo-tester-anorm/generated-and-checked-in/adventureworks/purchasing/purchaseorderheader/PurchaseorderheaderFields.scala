/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package purchaseorderheader

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.purchasing.shipmethod.ShipmethodId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.IdField
import typo.dsl.SqlExpr.OptField

trait PurchaseorderheaderFields[Row] {
  val purchaseorderid: IdField[PurchaseorderheaderId, Row]
  val revisionnumber: Field[TypoShort, Row]
  val status: Field[TypoShort, Row]
  val employeeid: Field[BusinessentityId, Row]
  val vendorid: Field[BusinessentityId, Row]
  val shipmethodid: Field[ShipmethodId, Row]
  val orderdate: Field[TypoLocalDateTime, Row]
  val shipdate: OptField[TypoLocalDateTime, Row]
  val subtotal: Field[BigDecimal, Row]
  val taxamt: Field[BigDecimal, Row]
  val freight: Field[BigDecimal, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}
object PurchaseorderheaderFields extends PurchaseorderheaderStructure[PurchaseorderheaderRow](None, identity, (_, x) => x)
