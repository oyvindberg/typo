package testdb.pg_catalog

import java.sql.Connection

trait PgStatProgressCreateIndexRepo {
  def selectAll(implicit c: Connection): List[PgStatProgressCreateIndexRow]
  def selectByFieldValues(fieldValues: List[PgStatProgressCreateIndexFieldValue[_]])(implicit c: Connection): List[PgStatProgressCreateIndexRow]
}
