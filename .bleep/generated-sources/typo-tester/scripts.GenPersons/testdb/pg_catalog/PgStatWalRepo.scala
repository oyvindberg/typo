package testdb.pg_catalog

import java.sql.Connection

trait PgStatWalRepo {
  def selectAll(implicit c: Connection): List[PgStatWalRow]
  def selectByFieldValues(fieldValues: List[PgStatWalFieldValue[_]])(implicit c: Connection): List[PgStatWalRow]
}
