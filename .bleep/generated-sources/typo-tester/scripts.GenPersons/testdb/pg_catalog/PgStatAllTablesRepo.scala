package testdb
package pg_catalog

import java.sql.Connection

trait PgStatAllTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatAllTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatAllTablesFieldValue[_]])(implicit c: Connection): List[PgStatAllTablesRow]
}
