package testdb
package pg_catalog

import java.sql.Connection

trait PgStatUserTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatUserTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatUserTablesFieldValue[_]])(implicit c: Connection): List[PgStatUserTablesRow]
}
