/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package plph

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PlphViewRepo {
  def select: SelectBuilder[PlphViewFields, PlphViewRow]
  def selectAll: Stream[ConnectionIO, PlphViewRow]
}
