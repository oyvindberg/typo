/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_locks

import java.sql.Connection
import typo.dsl.SelectBuilder

trait PgLocksViewRepo {
  def select: SelectBuilder[PgLocksViewFields, PgLocksViewRow]
  def selectAll(implicit c: Connection): List[PgLocksViewRow]
}