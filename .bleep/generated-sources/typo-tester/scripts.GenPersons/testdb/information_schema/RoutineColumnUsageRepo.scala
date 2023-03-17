package testdb
package information_schema

import java.sql.Connection

trait RoutineColumnUsageRepo {
  def selectAll(implicit c: Connection): List[RoutineColumnUsageRow]
  def selectByFieldValues(fieldValues: List[RoutineColumnUsageFieldValue[_]])(implicit c: Connection): List[RoutineColumnUsageRow]
}
