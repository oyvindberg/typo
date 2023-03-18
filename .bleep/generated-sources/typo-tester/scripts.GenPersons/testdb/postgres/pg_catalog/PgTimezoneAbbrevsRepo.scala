package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTimezoneAbbrevsRepo {
  def selectAll(implicit c: Connection): List[PgTimezoneAbbrevsRow]
  def selectByFieldValues(fieldValues: List[PgTimezoneAbbrevsFieldValue[_]])(implicit c: Connection): List[PgTimezoneAbbrevsRow]
}
