/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package `_pg_foreign_tables`

import doobie.free.connection.ConnectionIO
import fs2.Stream

trait PgForeignTablesViewRepo {
  def selectAll: Stream[ConnectionIO, PgForeignTablesViewRow]
}