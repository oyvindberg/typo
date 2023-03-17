package testdb.pg_catalog

import java.sql.Connection

trait PgPreparedXactsRepo {
  def selectAll(implicit c: Connection): List[PgPreparedXactsRow]
  def selectByFieldValues(fieldValues: List[PgPreparedXactsFieldValue[_]])(implicit c: Connection): List[PgPreparedXactsRow]
}
