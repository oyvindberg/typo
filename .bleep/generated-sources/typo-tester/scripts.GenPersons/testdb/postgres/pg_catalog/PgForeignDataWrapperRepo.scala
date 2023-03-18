package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgForeignDataWrapperRepo {
  def selectAll(implicit c: Connection): List[PgForeignDataWrapperRow]
  def selectByFieldValues(fieldValues: List[PgForeignDataWrapperFieldValue[_]])(implicit c: Connection): List[PgForeignDataWrapperRow]
}
