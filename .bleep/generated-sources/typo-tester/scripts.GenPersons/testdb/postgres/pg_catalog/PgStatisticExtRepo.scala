package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatisticExtRepo {
  def selectAll(implicit c: Connection): List[PgStatisticExtRow]
  def selectByFieldValues(fieldValues: List[PgStatisticExtFieldValue[_]])(implicit c: Connection): List[PgStatisticExtRow]
}
