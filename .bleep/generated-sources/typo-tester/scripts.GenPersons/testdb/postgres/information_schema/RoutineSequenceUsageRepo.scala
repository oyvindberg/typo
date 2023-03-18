package testdb
package postgres
package information_schema

import java.sql.Connection

trait RoutineSequenceUsageRepo {
  def selectAll(implicit c: Connection): List[RoutineSequenceUsageRow]
  def selectByFieldValues(fieldValues: List[RoutineSequenceUsageFieldValue[_]])(implicit c: Connection): List[RoutineSequenceUsageRow]
}
