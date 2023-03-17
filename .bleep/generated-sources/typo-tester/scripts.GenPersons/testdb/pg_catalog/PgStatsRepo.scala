package testdb
package pg_catalog

import java.sql.Connection

trait PgStatsRepo {
  def selectAll(implicit c: Connection): List[PgStatsRow]
  def selectByFieldValues(fieldValues: List[PgStatsFieldValue[_]])(implicit c: Connection): List[PgStatsRow]
}
