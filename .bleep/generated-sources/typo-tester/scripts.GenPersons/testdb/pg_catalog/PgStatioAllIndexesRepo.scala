package testdb.pg_catalog

import java.sql.Connection

trait PgStatioAllIndexesRepo {
  def selectAll(implicit c: Connection): List[PgStatioAllIndexesRow]
  def selectByFieldValues(fieldValues: List[PgStatioAllIndexesFieldValue[_]])(implicit c: Connection): List[PgStatioAllIndexesRow]
}
