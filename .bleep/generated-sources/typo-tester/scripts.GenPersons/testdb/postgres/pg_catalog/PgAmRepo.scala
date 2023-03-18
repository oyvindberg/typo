package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAmRepo {
  def selectAll(implicit c: Connection): List[PgAmRow]
  def selectByFieldValues(fieldValues: List[PgAmFieldValue[_]])(implicit c: Connection): List[PgAmRow]
}
