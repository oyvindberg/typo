package testdb
package information_schema

import java.sql.Connection

trait CheckConstraintRoutineUsageRepo {
  def selectAll(implicit c: Connection): List[CheckConstraintRoutineUsageRow]
  def selectByFieldValues(fieldValues: List[CheckConstraintRoutineUsageFieldValue[_]])(implicit c: Connection): List[CheckConstraintRoutineUsageRow]
}
