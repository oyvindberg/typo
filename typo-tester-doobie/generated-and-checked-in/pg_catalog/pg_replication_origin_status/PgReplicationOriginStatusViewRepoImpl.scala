/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_replication_origin_status

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream

object PgReplicationOriginStatusViewRepoImpl extends PgReplicationOriginStatusViewRepo {
  override def selectAll: Stream[ConnectionIO, PgReplicationOriginStatusViewRow] = {
    sql"select local_id, external_id, remote_lsn, local_lsn from pg_catalog.pg_replication_origin_status".query[PgReplicationOriginStatusViewRow].stream
  }
}