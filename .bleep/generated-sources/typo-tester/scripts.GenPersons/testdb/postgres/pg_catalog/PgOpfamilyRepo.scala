package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgOpfamilyRepo {
  def selectAll(implicit c: Connection): List[PgOpfamilyRow]
  def selectByFieldValues(fieldValues: List[PgOpfamilyFieldValue[_]])(implicit c: Connection): List[PgOpfamilyRow]
}
