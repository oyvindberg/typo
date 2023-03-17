package testdb
package pg_catalog

import java.sql.Connection

trait PgUserMappingsRepo {
  def selectAll(implicit c: Connection): List[PgUserMappingsRow]
  def selectByFieldValues(fieldValues: List[PgUserMappingsFieldValue[_]])(implicit c: Connection): List[PgUserMappingsRow]
}
