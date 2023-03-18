package testdb
package postgres
package information_schema

import java.sql.Connection

trait RoutineTableUsageRepo {
  def selectAll(implicit c: Connection): List[RoutineTableUsageRow]
  def selectByFieldValues(fieldValues: List[RoutineTableUsageFieldValue[_]])(implicit c: Connection): List[RoutineTableUsageRow]
}
