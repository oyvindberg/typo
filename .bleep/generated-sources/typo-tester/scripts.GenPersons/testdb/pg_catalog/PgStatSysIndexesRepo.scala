package testdb
package pg_catalog

import java.sql.Connection

trait PgStatSysIndexesRepo {
  def selectAll(implicit c: Connection): List[PgStatSysIndexesRow]
  def selectByFieldValues(fieldValues: List[PgStatSysIndexesFieldValue[_]])(implicit c: Connection): List[PgStatSysIndexesRow]
}
