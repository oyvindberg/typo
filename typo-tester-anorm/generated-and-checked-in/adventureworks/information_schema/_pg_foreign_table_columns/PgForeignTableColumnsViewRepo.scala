/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package `_pg_foreign_table_columns`

import java.sql.Connection
import typo.dsl.SelectBuilder

trait PgForeignTableColumnsViewRepo {
  def select: SelectBuilder[PgForeignTableColumnsViewFields, PgForeignTableColumnsViewRow]
  def selectAll(implicit c: Connection): List[PgForeignTableColumnsViewRow]
}