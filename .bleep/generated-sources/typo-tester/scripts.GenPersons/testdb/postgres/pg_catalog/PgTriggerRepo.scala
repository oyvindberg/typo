package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTriggerRepo {
  def selectAll(implicit c: Connection): List[PgTriggerRow]
  def selectByFieldValues(fieldValues: List[PgTriggerFieldValue[_]])(implicit c: Connection): List[PgTriggerRow]
}
