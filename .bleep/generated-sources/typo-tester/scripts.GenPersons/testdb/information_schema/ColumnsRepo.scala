package testdb
package information_schema

import java.sql.Connection

trait ColumnsRepo {
  def selectAll(implicit c: Connection): List[ColumnsRow]
  def selectByFieldValues(fieldValues: List[ColumnsFieldValue[_]])(implicit c: Connection): List[ColumnsRow]
}
