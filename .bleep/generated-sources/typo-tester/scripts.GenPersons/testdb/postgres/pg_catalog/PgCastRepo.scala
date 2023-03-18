package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgCastRepo {
  def selectAll(implicit c: Connection): List[PgCastRow]
  def selectByFieldValues(fieldValues: List[PgCastFieldValue[_]])(implicit c: Connection): List[PgCastRow]
}
