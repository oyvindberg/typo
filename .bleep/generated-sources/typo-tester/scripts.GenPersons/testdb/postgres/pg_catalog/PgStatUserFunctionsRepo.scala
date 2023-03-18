package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatUserFunctionsRepo {
  def selectAll(implicit c: Connection): List[PgStatUserFunctionsRow]
  def selectByFieldValues(fieldValues: List[PgStatUserFunctionsFieldValue[_]])(implicit c: Connection): List[PgStatUserFunctionsRow]
}
