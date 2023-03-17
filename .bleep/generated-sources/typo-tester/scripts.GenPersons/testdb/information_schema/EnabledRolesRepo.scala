package testdb.information_schema

import java.sql.Connection

trait EnabledRolesRepo {
  def selectAll(implicit c: Connection): List[EnabledRolesRow]
  def selectByFieldValues(fieldValues: List[EnabledRolesFieldValue[_]])(implicit c: Connection): List[EnabledRolesRow]
}
