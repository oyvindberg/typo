package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatioSysIndexesRepo {
  def selectAll(implicit c: Connection): List[PgStatioSysIndexesRow]
  def selectByFieldValues(fieldValues: List[PgStatioSysIndexesFieldValue[_]])(implicit c: Connection): List[PgStatioSysIndexesRow]
}
