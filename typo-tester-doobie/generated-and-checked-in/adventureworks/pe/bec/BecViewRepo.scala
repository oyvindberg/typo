/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package bec

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait BecViewRepo {
  def select: SelectBuilder[BecViewFields, BecViewRow]
  def selectAll: Stream[ConnectionIO, BecViewRow]
}
