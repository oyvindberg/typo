package testdb.pg_catalog

import java.sql.Connection

trait PgPublicationTablesRepo {
  def selectAll(implicit c: Connection): List[PgPublicationTablesRow]
  def selectByFieldValues(fieldValues: List[PgPublicationTablesFieldValue[_]])(implicit c: Connection): List[PgPublicationTablesRow]
}
