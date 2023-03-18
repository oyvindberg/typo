package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatBgwriterRepo {
  def selectAll(implicit c: Connection): List[PgStatBgwriterRow]
  def selectByFieldValues(fieldValues: List[PgStatBgwriterFieldValue[_]])(implicit c: Connection): List[PgStatBgwriterRow]
}
