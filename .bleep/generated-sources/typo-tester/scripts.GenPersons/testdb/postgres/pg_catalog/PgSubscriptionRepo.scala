package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgSubscriptionRepo {
  def selectAll(implicit c: Connection): List[PgSubscriptionRow]
  def selectByFieldValues(fieldValues: List[PgSubscriptionFieldValue[_]])(implicit c: Connection): List[PgSubscriptionRow]
}
