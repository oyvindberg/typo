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
import typo.dsl.SelectBuilder

trait PgForeignTablesViewRepo {
  def select: SelectBuilder[PgForeignTablesViewFields, PgForeignTablesViewRow]
  def selectAll: Stream[ConnectionIO, PgForeignTablesViewRow]
}