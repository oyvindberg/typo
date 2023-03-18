package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatioSysTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatioSysTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatioSysTablesFieldValue[_]])(implicit c: Connection): List[PgStatioSysTablesRow]
}
