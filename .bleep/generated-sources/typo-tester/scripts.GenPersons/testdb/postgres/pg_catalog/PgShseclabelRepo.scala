package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgShseclabelRepo {
  def selectAll(implicit c: Connection): List[PgShseclabelRow]
  def selectByFieldValues(fieldValues: List[PgShseclabelFieldValue[_]])(implicit c: Connection): List[PgShseclabelRow]
}
