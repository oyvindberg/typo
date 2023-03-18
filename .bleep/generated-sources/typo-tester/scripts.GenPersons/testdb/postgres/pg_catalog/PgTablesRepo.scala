package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTablesRepo {
  def selectAll(implicit c: Connection): List[PgTablesRow]
  def selectByFieldValues(fieldValues: List[PgTablesFieldValue[_]])(implicit c: Connection): List[PgTablesRow]
}
