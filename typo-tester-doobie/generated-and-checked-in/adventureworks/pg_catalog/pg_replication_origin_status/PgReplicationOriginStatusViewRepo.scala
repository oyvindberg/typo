/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_replication_origin_status

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PgReplicationOriginStatusViewRepo {
  def select: SelectBuilder[PgReplicationOriginStatusViewFields, PgReplicationOriginStatusViewRow]
  def selectAll: Stream[ConnectionIO, PgReplicationOriginStatusViewRow]
}