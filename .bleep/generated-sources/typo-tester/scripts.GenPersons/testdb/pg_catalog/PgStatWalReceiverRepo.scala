package testdb.pg_catalog

import java.sql.Connection

trait PgStatWalReceiverRepo {
  def selectAll(implicit c: Connection): List[PgStatWalReceiverRow]
  def selectByFieldValues(fieldValues: List[PgStatWalReceiverFieldValue[_]])(implicit c: Connection): List[PgStatWalReceiverRow]
}
