package testdb.pg_catalog

import java.sql.Connection

trait PgStatXactUserTablesRepo {
  def selectAll(implicit c: Connection): List[PgStatXactUserTablesRow]
  def selectByFieldValues(fieldValues: List[PgStatXactUserTablesFieldValue[_]])(implicit c: Connection): List[PgStatXactUserTablesRow]
}
