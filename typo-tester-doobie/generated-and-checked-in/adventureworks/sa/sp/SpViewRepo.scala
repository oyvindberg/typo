/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sp

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait SpViewRepo {
  def select: SelectBuilder[SpViewFields, SpViewRow]
  def selectAll: Stream[ConnectionIO, SpViewRow]
}
