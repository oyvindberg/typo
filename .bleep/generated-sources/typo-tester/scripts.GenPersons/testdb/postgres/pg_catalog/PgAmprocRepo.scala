package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAmprocRepo {
  def selectAll(implicit c: Connection): List[PgAmprocRow]
  def selectByFieldValues(fieldValues: List[PgAmprocFieldValue[_]])(implicit c: Connection): List[PgAmprocRow]
}
