/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pch

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PchViewRepo {
  def select: SelectBuilder[PchViewFields, PchViewRow]
  def selectAll: Stream[ConnectionIO, PchViewRow]
}