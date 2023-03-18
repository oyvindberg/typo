package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgDbRoleSettingRepo {
  def selectAll(implicit c: Connection): List[PgDbRoleSettingRow]
  def selectByFieldValues(fieldValues: List[PgDbRoleSettingFieldValue[_]])(implicit c: Connection): List[PgDbRoleSettingRow]
}
