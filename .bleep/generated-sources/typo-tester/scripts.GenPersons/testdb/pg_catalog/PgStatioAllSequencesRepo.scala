package testdb
package pg_catalog

import java.sql.Connection

trait PgStatioAllSequencesRepo {
  def selectAll(implicit c: Connection): List[PgStatioAllSequencesRow]
  def selectByFieldValues(fieldValues: List[PgStatioAllSequencesFieldValue[_]])(implicit c: Connection): List[PgStatioAllSequencesRow]
}
