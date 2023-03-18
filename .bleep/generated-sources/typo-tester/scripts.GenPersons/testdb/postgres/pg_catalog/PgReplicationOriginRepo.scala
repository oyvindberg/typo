package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgReplicationOriginRepo {
  def selectAll(implicit c: Connection): List[PgReplicationOriginRow]
  def selectByFieldValues(fieldValues: List[PgReplicationOriginFieldValue[_]])(implicit c: Connection): List[PgReplicationOriginRow]
}
