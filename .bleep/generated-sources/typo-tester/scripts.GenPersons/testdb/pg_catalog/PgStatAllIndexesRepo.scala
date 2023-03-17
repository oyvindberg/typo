package testdb
package pg_catalog

import java.sql.Connection

trait PgStatAllIndexesRepo {
  def selectAll(implicit c: Connection): List[PgStatAllIndexesRow]
  def selectByFieldValues(fieldValues: List[PgStatAllIndexesFieldValue[_]])(implicit c: Connection): List[PgStatAllIndexesRow]
}
