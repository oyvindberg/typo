/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package psc

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PscViewRepo {
  def select: SelectBuilder[PscViewFields, PscViewRow]
  def selectAll: Stream[ConnectionIO, PscViewRow]
}
