package testdb.pg_catalog

import java.sql.Connection

trait PgStatsExtRepo {
  def selectAll(implicit c: Connection): List[PgStatsExtRow]
  def selectByFieldValues(fieldValues: List[PgStatsExtFieldValue[_]])(implicit c: Connection): List[PgStatsExtRow]
}
