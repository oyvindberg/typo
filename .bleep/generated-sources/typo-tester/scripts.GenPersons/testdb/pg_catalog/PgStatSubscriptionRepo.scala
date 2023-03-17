package testdb.pg_catalog

import java.sql.Connection

trait PgStatSubscriptionRepo {
  def selectAll(implicit c: Connection): List[PgStatSubscriptionRow]
  def selectByFieldValues(fieldValues: List[PgStatSubscriptionFieldValue[_]])(implicit c: Connection): List[PgStatSubscriptionRow]
}
