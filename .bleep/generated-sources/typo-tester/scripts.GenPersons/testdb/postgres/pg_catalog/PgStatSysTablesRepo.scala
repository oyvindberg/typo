package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatSysTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatSysTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatSysTablesFieldValue[_]])(implicit c: Connection): List[PgStatSysTablesRow]
}
