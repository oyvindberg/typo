package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatisticExtDataRepo {
  def selectAll(implicit c: Connection): List[PgStatisticExtDataRow]
  def selectByFieldValues(fieldValues: List[PgStatisticExtDataFieldValue[_]])(implicit c: Connection): List[PgStatisticExtDataRow]
}
