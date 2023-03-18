package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgDatabaseRepo {
  def selectAll(implicit c: Connection): List[PgDatabaseRow]
  def selectByFieldValues(fieldValues: List[PgDatabaseFieldValue[_]])(implicit c: Connection): List[PgDatabaseRow]
}
