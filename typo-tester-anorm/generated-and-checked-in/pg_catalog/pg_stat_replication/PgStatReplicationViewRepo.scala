/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_replication

import java.sql.Connection

trait PgStatReplicationViewRepo {
  def selectAll(implicit c: Connection): List[PgStatReplicationViewRow]
}