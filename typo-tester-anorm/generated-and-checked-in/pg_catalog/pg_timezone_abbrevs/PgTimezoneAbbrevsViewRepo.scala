/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_timezone_abbrevs

import java.sql.Connection
import typo.dsl.SelectBuilder

trait PgTimezoneAbbrevsViewRepo {
  def select: SelectBuilder[PgTimezoneAbbrevsViewFields, PgTimezoneAbbrevsViewRow]
  def selectAll(implicit c: Connection): List[PgTimezoneAbbrevsViewRow]
}