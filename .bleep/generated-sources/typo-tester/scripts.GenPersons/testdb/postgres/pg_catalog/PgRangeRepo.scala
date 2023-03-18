package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgRangeRepo {
  def selectAll(implicit c: Connection): List[PgRangeRow]
  def selectByFieldValues(fieldValues: List[PgRangeFieldValue[_]])(implicit c: Connection): List[PgRangeRow]
}
