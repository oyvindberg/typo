/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package vproductanddescription

import java.sql.Connection
import typo.dsl.SelectBuilder

trait VproductanddescriptionMVRepo {
  def select: SelectBuilder[VproductanddescriptionMVFields, VproductanddescriptionMVRow]
  def selectAll(implicit c: Connection): List[VproductanddescriptionMVRow]
}
