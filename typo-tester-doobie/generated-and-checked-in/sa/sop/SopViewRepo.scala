/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sop

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait SopViewRepo {
  def select: SelectBuilder[SopViewFields, SopViewRow]
  def selectAll: Stream[ConnectionIO, SopViewRow]
}