package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgViewsRepo {
  def selectAll(implicit c: Connection): List[PgViewsRow]
  def selectByFieldValues(fieldValues: List[PgViewsFieldValue[_]])(implicit c: Connection): List[PgViewsRow]
}
