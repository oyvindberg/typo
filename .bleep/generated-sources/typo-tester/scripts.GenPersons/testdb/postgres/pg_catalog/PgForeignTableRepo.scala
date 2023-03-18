package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgForeignTableRepo {
  def selectAll(implicit c: Connection): List[PgForeignTableRow]
  def selectByFieldValues(fieldValues: List[PgForeignTableFieldValue[_]])(implicit c: Connection): List[PgForeignTableRow]
}
