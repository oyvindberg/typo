/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_group

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PgGroupViewRepo {
  def select: SelectBuilder[PgGroupViewFields, PgGroupViewRow]
  def selectAll: Stream[ConnectionIO, PgGroupViewRow]
}