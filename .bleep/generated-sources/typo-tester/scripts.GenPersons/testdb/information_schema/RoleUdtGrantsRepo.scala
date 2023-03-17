package testdb
package information_schema

import java.sql.Connection

trait RoleUdtGrantsRepo {
  def selectAll(implicit c: Connection): List[RoleUdtGrantsRow]
  def selectByFieldValues(fieldValues: List[RoleUdtGrantsFieldValue[_]])(implicit c: Connection): List[RoleUdtGrantsRow]
}
