package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgPublicationRepo {
  def selectAll(implicit c: Connection): List[PgPublicationRow]
  def selectByFieldValues(fieldValues: List[PgPublicationFieldValue[_]])(implicit c: Connection): List[PgPublicationRow]
}
