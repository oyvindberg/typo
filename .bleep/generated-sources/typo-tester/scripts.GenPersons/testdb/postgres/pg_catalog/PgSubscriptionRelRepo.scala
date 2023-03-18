package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgSubscriptionRelRepo {
  def selectAll(implicit c: Connection): List[PgSubscriptionRelRow]
  def selectByFieldValues(fieldValues: List[PgSubscriptionRelFieldValue[_]])(implicit c: Connection): List[PgSubscriptionRelRow]
}
