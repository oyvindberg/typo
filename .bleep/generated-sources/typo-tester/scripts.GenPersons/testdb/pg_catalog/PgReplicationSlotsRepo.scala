package testdb.pg_catalog

import java.sql.Connection

trait PgReplicationSlotsRepo {
  def selectAll(implicit c: Connection): List[PgReplicationSlotsRow]
  def selectByFieldValues(fieldValues: List[PgReplicationSlotsFieldValue[_]])(implicit c: Connection): List[PgReplicationSlotsRow]
}
