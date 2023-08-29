/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_config

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PgConfigViewRepo {
  def select: SelectBuilder[PgConfigViewFields, PgConfigViewRow]
  def selectAll: Stream[ConnectionIO, PgConfigViewRow]
}