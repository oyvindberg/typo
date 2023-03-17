package testdb
package information_schema

import java.sql.Connection

trait TriggeredUpdateColumnsRepo {
  def selectAll(implicit c: Connection): List[TriggeredUpdateColumnsRow]
  def selectByFieldValues(fieldValues: List[TriggeredUpdateColumnsFieldValue[_]])(implicit c: Connection): List[TriggeredUpdateColumnsRow]
}
