package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgIndexRepo {
  def selectAll(implicit c: Connection): List[PgIndexRow]
  def selectByFieldValues(fieldValues: List[PgIndexFieldValue[_]])(implicit c: Connection): List[PgIndexRow]
}
