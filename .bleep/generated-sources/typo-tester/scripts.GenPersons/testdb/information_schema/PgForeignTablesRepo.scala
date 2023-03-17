package testdb
package information_schema

import java.sql.Connection

trait PgForeignTablesRepo {
  def selectAll(implicit c: Connection): List[PgForeignTablesRow]
  def selectByFieldValues(fieldValues: List[PgForeignTablesFieldValue[_]])(implicit c: Connection): List[PgForeignTablesRow]
}
