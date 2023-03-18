package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgSettingsRepo {
  def selectAll(implicit c: Connection): List[PgSettingsRow]
  def selectByFieldValues(fieldValues: List[PgSettingsFieldValue[_]])(implicit c: Connection): List[PgSettingsRow]
}
