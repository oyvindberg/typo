/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pmpdc

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PmpdcViewRepo {
  def select: SelectBuilder[PmpdcViewFields, PmpdcViewRow]
  def selectAll: Stream[ConnectionIO, PmpdcViewRow]
}