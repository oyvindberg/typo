/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_progress_copy

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PgStatProgressCopyViewRepo {
  def select: SelectBuilder[PgStatProgressCopyViewFields, PgStatProgressCopyViewRow]
  def selectAll: Stream[ConnectionIO, PgStatProgressCopyViewRow]
}