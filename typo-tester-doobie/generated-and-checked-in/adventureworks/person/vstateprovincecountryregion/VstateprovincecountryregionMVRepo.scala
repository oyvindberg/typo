/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package vstateprovincecountryregion

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait VstateprovincecountryregionMVRepo {
  def select: SelectBuilder[VstateprovincecountryregionMVFields, VstateprovincecountryregionMVRow]
  def selectAll: Stream[ConnectionIO, VstateprovincecountryregionMVRow]
}