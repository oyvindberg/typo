/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package vvendorwithaddresses

import java.sql.Connection
import typo.dsl.SelectBuilder

trait VvendorwithaddressesViewRepo {
  def select: SelectBuilder[VvendorwithaddressesViewFields, VvendorwithaddressesViewRow]
  def selectAll(implicit c: Connection): List[VvendorwithaddressesViewRow]
}
