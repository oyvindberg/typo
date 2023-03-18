package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgPreparedStatementsRepo {
  def selectAll(implicit c: Connection): List[PgPreparedStatementsRow]
  def selectByFieldValues(fieldValues: List[PgPreparedStatementsFieldValue[_]])(implicit c: Connection): List[PgPreparedStatementsRow]
}
