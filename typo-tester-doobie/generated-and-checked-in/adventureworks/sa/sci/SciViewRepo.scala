/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sci

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait SciViewRepo {
  def select: SelectBuilder[SciViewFields, SciViewRow]
  def selectAll: Stream[ConnectionIO, SciViewRow]
}
