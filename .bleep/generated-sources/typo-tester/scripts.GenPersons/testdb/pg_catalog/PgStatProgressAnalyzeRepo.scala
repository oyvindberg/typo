package testdb.pg_catalog

import java.sql.Connection

trait PgStatProgressAnalyzeRepo {
  def selectAll(implicit c: Connection): List[PgStatProgressAnalyzeRow]
  def selectByFieldValues(fieldValues: List[PgStatProgressAnalyzeFieldValue[_]])(implicit c: Connection): List[PgStatProgressAnalyzeRow]
}
