package testdb.pg_catalog

import java.sql.Connection

trait PgSequencesRepo {
  def selectAll(implicit c: Connection): List[PgSequencesRow]
  def selectByFieldValues(fieldValues: List[PgSequencesFieldValue[_]])(implicit c: Connection): List[PgSequencesRow]
}
