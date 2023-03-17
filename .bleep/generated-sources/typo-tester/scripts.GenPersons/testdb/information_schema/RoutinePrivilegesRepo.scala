package testdb
package information_schema

import java.sql.Connection

trait RoutinePrivilegesRepo {
  def selectAll(implicit c: Connection): List[RoutinePrivilegesRow]
  def selectByFieldValues(fieldValues: List[RoutinePrivilegesFieldValue[_]])(implicit c: Connection): List[RoutinePrivilegesRow]
}
