package testdb.pg_catalog

import java.sql.Connection

trait PgStatSlruRepo {
  def selectAll(implicit c: Connection): List[PgStatSlruRow]
  def selectByFieldValues(fieldValues: List[PgStatSlruFieldValue[_]])(implicit c: Connection): List[PgStatSlruRow]
}
