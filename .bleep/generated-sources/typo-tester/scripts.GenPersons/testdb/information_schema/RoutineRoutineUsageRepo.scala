package testdb.information_schema

import java.sql.Connection

trait RoutineRoutineUsageRepo {
  def selectAll(implicit c: Connection): List[RoutineRoutineUsageRow]
  def selectByFieldValues(fieldValues: List[RoutineRoutineUsageFieldValue[_]])(implicit c: Connection): List[RoutineRoutineUsageRow]
}
