package testdb
package information_schema

import java.sql.Connection

trait TablesRepo {
  def selectAll(implicit c: Connection): List[TablesRow]
  def selectByFieldValues(fieldValues: List[TablesFieldValue[_]])(implicit c: Connection): List[TablesRow]
}
