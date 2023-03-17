package testdb
package information_schema

import java.sql.Connection

trait RoleColumnGrantsRepo {
  def selectAll(implicit c: Connection): List[RoleColumnGrantsRow]
  def selectByFieldValues(fieldValues: List[RoleColumnGrantsFieldValue[_]])(implicit c: Connection): List[RoleColumnGrantsRow]
}
