/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package `_pg_foreign_servers`

import java.sql.Connection

trait PgForeignServersViewRepo {
  def selectAll(implicit c: Connection): List[PgForeignServersViewRow]
}