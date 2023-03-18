package testdb
package postgres
package information_schema

import java.sql.Connection

trait ViewColumnUsageRepo {
  def selectAll(implicit c: Connection): List[ViewColumnUsageRow]
  def selectByFieldValues(fieldValues: List[ViewColumnUsageFieldValue[_]])(implicit c: Connection): List[ViewColumnUsageRow]
}
