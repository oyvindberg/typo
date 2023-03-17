package testdb.pg_catalog

import java.sql.Connection

trait PgStatProgressBasebackupRepo {
  def selectAll(implicit c: Connection): List[PgStatProgressBasebackupRow]
  def selectByFieldValues(fieldValues: List[PgStatProgressBasebackupFieldValue[_]])(implicit c: Connection): List[PgStatProgressBasebackupRow]
}
