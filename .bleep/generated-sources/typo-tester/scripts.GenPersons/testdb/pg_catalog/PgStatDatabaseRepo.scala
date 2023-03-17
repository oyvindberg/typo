package testdb.pg_catalog

import java.sql.Connection

trait PgStatDatabaseRepo {
  def selectAll(implicit c: Connection): List[PgStatDatabaseRow]
  def selectByFieldValues(fieldValues: List[PgStatDatabaseFieldValue[_]])(implicit c: Connection): List[PgStatDatabaseRow]
}
