package testdb.pg_catalog

import java.sql.Connection

trait PgStatioUserIndexesRepo {
  def selectAll(implicit c: Connection): List[PgStatioUserIndexesRow]
  def selectByFieldValues(fieldValues: List[PgStatioUserIndexesFieldValue[_]])(implicit c: Connection): List[PgStatioUserIndexesRow]
}
