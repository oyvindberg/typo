package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgCollationRepo {
  def selectAll(implicit c: Connection): List[PgCollationRow]
  def selectByFieldValues(fieldValues: List[PgCollationFieldValue[_]])(implicit c: Connection): List[PgCollationRow]
}
