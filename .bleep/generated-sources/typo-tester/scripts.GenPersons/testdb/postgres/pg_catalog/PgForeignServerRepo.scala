package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgForeignServerRepo {
  def selectAll(implicit c: Connection): List[PgForeignServerRow]
  def selectByFieldValues(fieldValues: List[PgForeignServerFieldValue[_]])(implicit c: Connection): List[PgForeignServerRow]
}
