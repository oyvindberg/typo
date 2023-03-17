package testdb
package information_schema

import java.sql.Connection

trait TablePrivilegesRepo {
  def selectAll(implicit c: Connection): List[TablePrivilegesRow]
  def selectByFieldValues(fieldValues: List[TablePrivilegesFieldValue[_]])(implicit c: Connection): List[TablePrivilegesRow]
}
