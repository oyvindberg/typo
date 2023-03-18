package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgExtensionRepo {
  def selectAll(implicit c: Connection): List[PgExtensionRow]
  def selectByFieldValues(fieldValues: List[PgExtensionFieldValue[_]])(implicit c: Connection): List[PgExtensionRow]
}
