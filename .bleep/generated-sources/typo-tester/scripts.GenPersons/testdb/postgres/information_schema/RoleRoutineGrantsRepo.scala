package testdb
package postgres
package information_schema

import java.sql.Connection

trait RoleRoutineGrantsRepo {
  def selectAll(implicit c: Connection): List[RoleRoutineGrantsRow]
  def selectByFieldValues(fieldValues: List[RoleRoutineGrantsFieldValue[_]])(implicit c: Connection): List[RoleRoutineGrantsRow]
}
