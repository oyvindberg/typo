/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_progress_vacuum

import java.sql.Connection
import typo.dsl.SelectBuilder

trait PgStatProgressVacuumViewRepo {
  def select: SelectBuilder[PgStatProgressVacuumViewFields, PgStatProgressVacuumViewRow]
  def selectAll(implicit c: Connection): List[PgStatProgressVacuumViewRow]
}