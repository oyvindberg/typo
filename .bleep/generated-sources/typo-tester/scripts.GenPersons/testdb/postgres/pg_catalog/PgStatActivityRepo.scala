package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatActivityRepo {
  def selectAll(implicit c: Connection): List[PgStatActivityRow]
  def selectByFieldValues(fieldValues: List[PgStatActivityFieldValue[_]])(implicit c: Connection): List[PgStatActivityRow]
}
