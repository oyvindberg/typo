package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgUserMappingRepo {
  def selectAll(implicit c: Connection): List[PgUserMappingRow]
  def selectByFieldValues(fieldValues: List[PgUserMappingFieldValue[_]])(implicit c: Connection): List[PgUserMappingRow]
}
