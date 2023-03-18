package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgLargeobjectRepo {
  def selectAll(implicit c: Connection): List[PgLargeobjectRow]
  def selectByFieldValues(fieldValues: List[PgLargeobjectFieldValue[_]])(implicit c: Connection): List[PgLargeobjectRow]
}
