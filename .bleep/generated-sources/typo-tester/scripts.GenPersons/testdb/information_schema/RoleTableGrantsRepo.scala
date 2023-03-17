package testdb.information_schema

import java.sql.Connection

trait RoleTableGrantsRepo {
  def selectAll(implicit c: Connection): List[RoleTableGrantsRow]
  def selectByFieldValues(fieldValues: List[RoleTableGrantsFieldValue[_]])(implicit c: Connection): List[RoleTableGrantsRow]
}
