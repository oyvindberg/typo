package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgProcRepo {
  def selectAll(implicit c: Connection): List[PgProcRow]
  def selectByFieldValues(fieldValues: List[PgProcFieldValue[_]])(implicit c: Connection): List[PgProcRow]
}
