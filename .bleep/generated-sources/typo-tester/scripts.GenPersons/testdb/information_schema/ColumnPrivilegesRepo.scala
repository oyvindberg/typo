package testdb.information_schema

import java.sql.Connection

trait ColumnPrivilegesRepo {
  def selectAll(implicit c: Connection): List[ColumnPrivilegesRow]
  def selectByFieldValues(fieldValues: List[ColumnPrivilegesFieldValue[_]])(implicit c: Connection): List[ColumnPrivilegesRow]
}
