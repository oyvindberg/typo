/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vindividualcustomer

import typo.dsl.SelectBuilder
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait VindividualcustomerViewRepo {
  def select: SelectBuilder[VindividualcustomerViewFields, VindividualcustomerViewRow]
  def selectAll: ZStream[ZConnection, Throwable, VindividualcustomerViewRow]
}
