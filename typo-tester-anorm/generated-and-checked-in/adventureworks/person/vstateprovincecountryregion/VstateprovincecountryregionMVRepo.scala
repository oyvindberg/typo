/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package vstateprovincecountryregion

import java.sql.Connection
import typo.dsl.SelectBuilder

trait VstateprovincecountryregionMVRepo {
  def select: SelectBuilder[VstateprovincecountryregionMVFields, VstateprovincecountryregionMVRow]
  def selectAll(implicit c: Connection): List[VstateprovincecountryregionMVRow]
}
