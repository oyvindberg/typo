package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgOpclassRepo {
  def selectAll(implicit c: Connection): List[PgOpclassRow]
  def selectByFieldValues(fieldValues: List[PgOpclassFieldValue[_]])(implicit c: Connection): List[PgOpclassRow]
}
