package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatisticRepo {
  def selectAll(implicit c: Connection): List[PgStatisticRow]
  def selectByFieldValues(fieldValues: List[PgStatisticFieldValue[_]])(implicit c: Connection): List[PgStatisticRow]
}
