package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatioUserSequencesRepo {
  def selectAll(implicit c: Connection): List[PgStatioUserSequencesRow]
  def selectByFieldValues(fieldValues: List[PgStatioUserSequencesFieldValue[_]])(implicit c: Connection): List[PgStatioUserSequencesRow]
}
