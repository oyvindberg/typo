package testdb
package information_schema

import java.sql.Connection

trait RoleUsageGrantsRepo {
  def selectAll(implicit c: Connection): List[RoleUsageGrantsRow]
  def selectByFieldValues(fieldValues: List[RoleUsageGrantsFieldValue[_]])(implicit c: Connection): List[RoleUsageGrantsRow]
}
