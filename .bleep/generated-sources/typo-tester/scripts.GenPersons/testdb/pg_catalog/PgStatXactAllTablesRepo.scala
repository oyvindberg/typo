package testdb.pg_catalog

import java.sql.Connection

trait PgStatXactAllTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatXactAllTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatXactAllTablesFieldValue[_]])(implicit c: Connection): List[PgStatXactAllTablesRow]
}
