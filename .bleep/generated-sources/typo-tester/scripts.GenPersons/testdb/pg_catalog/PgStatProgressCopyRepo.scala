package testdb
package pg_catalog

import java.sql.Connection

trait PgStatProgressCopyRepo {
  def selectAll(implicit c: Connection): List[PgStatProgressCopyRow]
  def selectByFieldValues(fieldValues: List[PgStatProgressCopyFieldValue[_]])(implicit c: Connection): List[PgStatProgressCopyRow]
}
