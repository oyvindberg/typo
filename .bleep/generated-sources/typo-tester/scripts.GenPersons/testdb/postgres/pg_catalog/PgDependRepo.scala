package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgDependRepo {
  def selectAll(implicit c: Connection): List[PgDependRow]
  def selectByFieldValues(fieldValues: List[PgDependFieldValue[_]])(implicit c: Connection): List[PgDependRow]
}
