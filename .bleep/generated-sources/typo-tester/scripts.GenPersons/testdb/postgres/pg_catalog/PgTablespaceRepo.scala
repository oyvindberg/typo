package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTablespaceRepo {
  def selectAll(implicit c: Connection): List[PgTablespaceRow]
  def selectByFieldValues(fieldValues: List[PgTablespaceFieldValue[_]])(implicit c: Connection): List[PgTablespaceRow]
}
