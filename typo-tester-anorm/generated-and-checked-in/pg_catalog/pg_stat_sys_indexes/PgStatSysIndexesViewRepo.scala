/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_sys_indexes

import java.sql.Connection
import typo.dsl.SelectBuilder

trait PgStatSysIndexesViewRepo {
  def select: SelectBuilder[PgStatSysIndexesViewFields, PgStatSysIndexesViewRow]
  def selectAll(implicit c: Connection): List[PgStatSysIndexesViewRow]
}