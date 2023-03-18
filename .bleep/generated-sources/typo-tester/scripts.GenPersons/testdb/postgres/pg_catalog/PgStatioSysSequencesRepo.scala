package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatioSysSequencesRepo {
  def selectAll(implicit c: Connection): List[PgStatioSysSequencesRow]
  def selectByFieldValues(fieldValues: List[PgStatioSysSequencesFieldValue[_]])(implicit c: Connection): List[PgStatioSysSequencesRow]
}
