package testdb
package pg_catalog

import java.sql.Connection

trait PgReplicationOriginStatusRepo {
  def selectAll(implicit c: Connection): List[PgReplicationOriginStatusRow]
  def selectByFieldValues(fieldValues: List[PgReplicationOriginStatusFieldValue[_]])(implicit c: Connection): List[PgReplicationOriginStatusRow]
}
