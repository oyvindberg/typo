package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatReplicationSlotsRepo {
  def selectAll(implicit c: Connection): List[PgStatReplicationSlotsRow]
  def selectByFieldValues(fieldValues: List[PgStatReplicationSlotsFieldValue[_]])(implicit c: Connection): List[PgStatReplicationSlotsRow]
}
