package testdb
package pg_catalog

import java.sql.Connection

trait PgLocksRepo {
  def selectAll(implicit c: Connection): List[PgLocksRow]
  def selectByFieldValues(fieldValues: List[PgLocksFieldValue[_]])(implicit c: Connection): List[PgLocksRow]
}
