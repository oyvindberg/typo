package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgEventTriggerRepo {
  def selectAll(implicit c: Connection): List[PgEventTriggerRow]
  def selectByFieldValues(fieldValues: List[PgEventTriggerFieldValue[_]])(implicit c: Connection): List[PgEventTriggerRow]
}
