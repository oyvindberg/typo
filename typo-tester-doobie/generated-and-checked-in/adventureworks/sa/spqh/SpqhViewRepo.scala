/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package spqh

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait SpqhViewRepo {
  def select: SelectBuilder[SpqhViewFields, SpqhViewRow]
  def selectAll: Stream[ConnectionIO, SpqhViewRow]
}
