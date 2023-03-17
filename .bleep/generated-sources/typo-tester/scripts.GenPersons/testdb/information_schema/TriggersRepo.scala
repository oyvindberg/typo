package testdb
package information_schema

import java.sql.Connection

trait TriggersRepo {
  def selectAll(implicit c: Connection): List[TriggersRow]
  def selectByFieldValues(fieldValues: List[TriggersFieldValue[_]])(implicit c: Connection): List[TriggersRow]
}
