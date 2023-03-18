package testdb
package postgres
package information_schema

import java.sql.Connection

trait UdtPrivilegesRepo {
  def selectAll(implicit c: Connection): List[UdtPrivilegesRow]
  def selectByFieldValues(fieldValues: List[UdtPrivilegesFieldValue[_]])(implicit c: Connection): List[UdtPrivilegesRow]
}
