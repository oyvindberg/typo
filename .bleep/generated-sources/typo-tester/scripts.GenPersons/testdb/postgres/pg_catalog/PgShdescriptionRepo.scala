package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgShdescriptionRepo {
  def selectAll(implicit c: Connection): List[PgShdescriptionRow]
  def selectByFieldValues(fieldValues: List[PgShdescriptionFieldValue[_]])(implicit c: Connection): List[PgShdescriptionRow]
}
