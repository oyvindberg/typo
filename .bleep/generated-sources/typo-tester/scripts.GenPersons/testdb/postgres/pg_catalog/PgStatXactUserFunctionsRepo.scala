package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatXactUserFunctionsRepo {
  def selectAll(implicit c: Connection): List[PgStatXactUserFunctionsRow]
  def selectByFieldValues(fieldValues: List[PgStatXactUserFunctionsFieldValue[_]])(implicit c: Connection): List[PgStatXactUserFunctionsRow]
}
