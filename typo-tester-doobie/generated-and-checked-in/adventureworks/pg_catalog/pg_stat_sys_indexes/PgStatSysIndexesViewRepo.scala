/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_sys_indexes

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PgStatSysIndexesViewRepo {
  def select: SelectBuilder[PgStatSysIndexesViewFields, PgStatSysIndexesViewRow]
  def selectAll: Stream[ConnectionIO, PgStatSysIndexesViewRow]
}