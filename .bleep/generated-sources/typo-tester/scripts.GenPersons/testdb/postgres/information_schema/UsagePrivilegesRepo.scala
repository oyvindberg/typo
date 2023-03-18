package testdb
package postgres
package information_schema

import java.sql.Connection

trait UsagePrivilegesRepo {
  def selectAll(implicit c: Connection): List[UsagePrivilegesRow]
  def selectByFieldValues(fieldValues: List[UsagePrivilegesFieldValue[_]])(implicit c: Connection): List[UsagePrivilegesRow]
}
