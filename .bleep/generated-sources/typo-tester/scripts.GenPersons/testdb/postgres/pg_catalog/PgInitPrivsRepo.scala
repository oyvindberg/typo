package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgInitPrivsRepo {
  def selectAll(implicit c: Connection): List[PgInitPrivsRow]
  def selectByFieldValues(fieldValues: List[PgInitPrivsFieldValue[_]])(implicit c: Connection): List[PgInitPrivsRow]
}
