/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vindividualcustomer

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait VindividualcustomerViewRepo {
  def select: SelectBuilder[VindividualcustomerViewFields, VindividualcustomerViewRow]
  def selectAll: Stream[ConnectionIO, VindividualcustomerViewRow]
}
