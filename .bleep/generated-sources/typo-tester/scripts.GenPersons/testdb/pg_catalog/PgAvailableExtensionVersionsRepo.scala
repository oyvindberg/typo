package testdb
package pg_catalog

import java.sql.Connection

trait PgAvailableExtensionVersionsRepo {
  def selectAll(implicit c: Connection): List[PgAvailableExtensionVersionsRow]
  def selectByFieldValues(fieldValues: List[PgAvailableExtensionVersionsFieldValue[_]])(implicit c: Connection): List[PgAvailableExtensionVersionsRow]
}
