package testdb.pg_catalog

import java.sql.Connection

trait PgStatArchiverRepo {
  def selectAll(implicit c: Connection): List[PgStatArchiverRow]
  def selectByFieldValues(fieldValues: List[PgStatArchiverFieldValue[_]])(implicit c: Connection): List[PgStatArchiverRow]
}
