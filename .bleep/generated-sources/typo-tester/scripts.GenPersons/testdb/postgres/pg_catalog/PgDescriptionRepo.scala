package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgDescriptionRepo {
  def selectAll(implicit c: Connection): List[PgDescriptionRow]
  def selectByFieldValues(fieldValues: List[PgDescriptionFieldValue[_]])(implicit c: Connection): List[PgDescriptionRow]
}
