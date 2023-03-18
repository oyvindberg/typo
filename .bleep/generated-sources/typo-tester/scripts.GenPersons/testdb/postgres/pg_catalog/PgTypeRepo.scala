package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTypeRepo {
  def selectAll(implicit c: Connection): List[PgTypeRow]
  def selectByFieldValues(fieldValues: List[PgTypeFieldValue[_]])(implicit c: Connection): List[PgTypeRow]
}
