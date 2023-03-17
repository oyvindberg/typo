package testdb.pg_catalog

import java.sql.Connection

trait PgStatUserIndexesRepo {
  def selectAll(implicit c: Connection): List[PgStatUserIndexesRow]
  def selectByFieldValues(fieldValues: List[PgStatUserIndexesFieldValue[_]])(implicit c: Connection): List[PgStatUserIndexesRow]
}
