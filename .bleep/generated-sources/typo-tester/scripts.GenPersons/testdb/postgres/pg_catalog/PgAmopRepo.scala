package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAmopRepo {
  def selectAll(implicit c: Connection): List[PgAmopRow]
  def selectByFieldValues(fieldValues: List[PgAmopFieldValue[_]])(implicit c: Connection): List[PgAmopRow]
}
