package testdb
package pg_catalog

import java.sql.Connection

trait PgStatsExtExprsRepo {
  def selectAll(implicit c: Connection): List[PgStatsExtExprsRow]
  def selectByFieldValues(fieldValues: List[PgStatsExtExprsFieldValue[_]])(implicit c: Connection): List[PgStatsExtExprsRow]
}
