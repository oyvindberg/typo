package testdb.pg_catalog

import java.sql.Connection

trait PgStatProgressClusterRepo {
  def selectAll(implicit c: Connection): List[PgStatProgressClusterRow]
  def selectByFieldValues(fieldValues: List[PgStatProgressClusterFieldValue[_]])(implicit c: Connection): List[PgStatProgressClusterRow]
}
