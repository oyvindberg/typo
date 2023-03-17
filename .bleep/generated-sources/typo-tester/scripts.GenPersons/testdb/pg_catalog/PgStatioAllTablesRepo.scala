package testdb.pg_catalog

import java.sql.Connection

trait PgStatioAllTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatioAllTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatioAllTablesFieldValue[_]])(implicit c: Connection): List[PgStatioAllTablesRow]
}
