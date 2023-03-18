package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatXactSysTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatXactSysTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatXactSysTablesFieldValue[_]])(implicit c: Connection): List[PgStatXactSysTablesRow]
}
