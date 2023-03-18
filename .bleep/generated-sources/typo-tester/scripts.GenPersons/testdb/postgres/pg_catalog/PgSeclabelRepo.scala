package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgSeclabelRepo {
  def selectAll(implicit c: Connection): List[PgSeclabelRow]
  def selectByFieldValues(fieldValues: List[PgSeclabelFieldValue[_]])(implicit c: Connection): List[PgSeclabelRow]
}
