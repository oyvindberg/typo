/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_progress_cluster

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

object PgStatProgressClusterViewRepoImpl extends PgStatProgressClusterViewRepo {
  override def select: SelectBuilder[PgStatProgressClusterViewFields, PgStatProgressClusterViewRow] = {
    SelectBuilderSql("pg_catalog.pg_stat_progress_cluster", PgStatProgressClusterViewFields, PgStatProgressClusterViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, PgStatProgressClusterViewRow] = {
    sql"select pid, datid, datname, relid, command, phase, cluster_index_relid, heap_tuples_scanned, heap_tuples_written, heap_blks_total, heap_blks_scanned, index_rebuild_count from pg_catalog.pg_stat_progress_cluster".query(PgStatProgressClusterViewRow.read).stream
  }
}