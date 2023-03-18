package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgClassRepo {
  def selectAll(implicit c: Connection): List[PgClassRow]
  def selectByFieldValues(fieldValues: List[PgClassFieldValue[_]])(implicit c: Connection): List[PgClassRow]
}
