/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package sr

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait SrViewRepo {
  def select: SelectBuilder[SrViewFields, SrViewRow]
  def selectAll: Stream[ConnectionIO, SrViewRow]
}