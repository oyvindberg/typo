package testdb.information_schema

import java.sql.Connection

trait ApplicableRolesRepo {
  def selectAll(implicit c: Connection): List[ApplicableRolesRow]
  def selectByFieldValues(fieldValues: List[ApplicableRolesFieldValue[_]])(implicit c: Connection): List[ApplicableRolesRow]
}
