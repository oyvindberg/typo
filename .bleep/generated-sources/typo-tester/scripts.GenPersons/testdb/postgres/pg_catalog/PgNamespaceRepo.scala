package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgNamespaceRepo {
  def selectAll(implicit c: Connection): List[PgNamespaceRow]
  def selectByFieldValues(fieldValues: List[PgNamespaceFieldValue[_]])(implicit c: Connection): List[PgNamespaceRow]
}
