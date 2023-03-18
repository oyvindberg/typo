package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAuthidRepo {
  def selectAll(implicit c: Connection): List[PgAuthidRow]
  def selectByFieldValues(fieldValues: List[PgAuthidFieldValue[_]])(implicit c: Connection): List[PgAuthidRow]
}
