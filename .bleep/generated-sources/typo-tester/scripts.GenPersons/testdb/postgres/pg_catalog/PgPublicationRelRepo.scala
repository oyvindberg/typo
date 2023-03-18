package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgPublicationRelRepo {
  def selectAll(implicit c: Connection): List[PgPublicationRelRow]
  def selectByFieldValues(fieldValues: List[PgPublicationRelFieldValue[_]])(implicit c: Connection): List[PgPublicationRelRow]
}
