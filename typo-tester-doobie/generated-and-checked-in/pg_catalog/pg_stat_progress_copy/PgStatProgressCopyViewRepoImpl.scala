/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_progress_copy

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream

object PgStatProgressCopyViewRepoImpl extends PgStatProgressCopyViewRepo {
  override def selectAll: Stream[ConnectionIO, PgStatProgressCopyViewRow] = {
    sql"""select pid, datid, datname, relid, command, "type", bytes_processed, bytes_total, tuples_processed, tuples_excluded from pg_catalog.pg_stat_progress_copy""".query[PgStatProgressCopyViewRow].stream
  }
}