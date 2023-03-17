package testdb
package pg_catalog

import java.sql.Connection

trait PgStatioUserTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatioUserTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatioUserTablesFieldValue[_]])(implicit c: Connection): List[PgStatioUserTablesRow]
}
