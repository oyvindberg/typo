package testdb
package information_schema

import java.sql.Connection

trait ViewRoutineUsageRepo {
  def selectAll(implicit c: Connection): List[ViewRoutineUsageRow]
  def selectByFieldValues(fieldValues: List[ViewRoutineUsageFieldValue[_]])(implicit c: Connection): List[ViewRoutineUsageRow]
}
