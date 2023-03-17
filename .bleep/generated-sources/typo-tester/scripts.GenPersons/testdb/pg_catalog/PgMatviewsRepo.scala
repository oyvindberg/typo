package testdb
package pg_catalog

import java.sql.Connection

trait PgMatviewsRepo {
  def selectAll(implicit c: Connection): List[PgMatviewsRow]
  def selectByFieldValues(fieldValues: List[PgMatviewsFieldValue[_]])(implicit c: Connection): List[PgMatviewsRow]
}
