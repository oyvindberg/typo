package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgShdependRepo {
  def selectAll(implicit c: Connection): List[PgShdependRow]
  def selectByFieldValues(fieldValues: List[PgShdependFieldValue[_]])(implicit c: Connection): List[PgShdependRow]
}
