package testdb
package postgres
package information_schema

import java.sql.Connection

trait ForeignTablesRepo {
  def selectAll(implicit c: Connection): List[ForeignTablesRow]
  def selectByFieldValues(fieldValues: List[ForeignTablesFieldValue[_]])(implicit c: Connection): List[ForeignTablesRow]
}
