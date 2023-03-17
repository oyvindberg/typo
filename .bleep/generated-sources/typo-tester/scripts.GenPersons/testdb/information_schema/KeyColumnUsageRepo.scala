package testdb
package information_schema

import java.sql.Connection

trait KeyColumnUsageRepo {
  def selectAll(implicit c: Connection): List[KeyColumnUsageRow]
  def selectByFieldValues(fieldValues: List[KeyColumnUsageFieldValue[_]])(implicit c: Connection): List[KeyColumnUsageRow]
}
