package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait AdministrableRoleAuthorizationsRepoImpl extends AdministrableRoleAuthorizationsRepo {
  override def selectAll(implicit c: Connection): List[AdministrableRoleAuthorizationsRow] = {
    SQL"""select grantee, role_name, is_grantable from information_schema.administrable_role_authorizations""".as(AdministrableRoleAuthorizationsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[AdministrableRoleAuthorizationsFieldValue[_]])(implicit c: Connection): List[AdministrableRoleAuthorizationsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case AdministrableRoleAuthorizationsFieldValue.grantee(value) => NamedParameter("grantee", ParameterValue.from(value))
          case AdministrableRoleAuthorizationsFieldValue.roleName(value) => NamedParameter("role_name", ParameterValue.from(value))
          case AdministrableRoleAuthorizationsFieldValue.isGrantable(value) => NamedParameter("is_grantable", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.administrable_role_authorizations where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(AdministrableRoleAuthorizationsRow.rowParser.*)
    }

  }
}
