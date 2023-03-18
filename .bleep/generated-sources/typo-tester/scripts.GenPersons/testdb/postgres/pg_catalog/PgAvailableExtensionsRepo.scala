package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAvailableExtensionsRepo {
  def selectAll(implicit c: Connection): List[PgAvailableExtensionsRow]
  def selectByFieldValues(fieldValues: List[PgAvailableExtensionsFieldValue[_]])(implicit c: Connection): List[PgAvailableExtensionsRow]
}
