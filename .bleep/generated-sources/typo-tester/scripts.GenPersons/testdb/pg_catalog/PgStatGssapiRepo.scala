package testdb.pg_catalog

import java.sql.Connection

trait PgStatGssapiRepo {
  def selectAll(implicit c: Connection): List[PgStatGssapiRow]
  def selectByFieldValues(fieldValues: List[PgStatGssapiFieldValue[_]])(implicit c: Connection): List[PgStatGssapiRow]
}
