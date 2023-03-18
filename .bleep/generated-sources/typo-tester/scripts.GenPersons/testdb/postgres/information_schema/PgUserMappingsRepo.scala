package testdb
package postgres
package information_schema

import java.sql.Connection

trait PgUserMappingsRepo {
  def selectAll(implicit c: Connection): List[PgUserMappingsRow]
  def selectByFieldValues(fieldValues: List[PgUserMappingsFieldValue[_]])(implicit c: Connection): List[PgUserMappingsRow]
}
