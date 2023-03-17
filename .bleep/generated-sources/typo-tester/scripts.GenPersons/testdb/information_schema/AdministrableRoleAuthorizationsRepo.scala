package testdb.information_schema

import java.sql.Connection

trait AdministrableRoleAuthorizationsRepo {
  def selectAll(implicit c: Connection): List[AdministrableRoleAuthorizationsRow]
  def selectByFieldValues(fieldValues: List[AdministrableRoleAuthorizationsFieldValue[_]])(implicit c: Connection): List[AdministrableRoleAuthorizationsRow]
}
