/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_wal_receiver

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PgStatWalReceiverViewRepo {
  def select: SelectBuilder[PgStatWalReceiverViewFields, PgStatWalReceiverViewRow]
  def selectAll: Stream[ConnectionIO, PgStatWalReceiverViewRow]
}