package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait RoleUdtGrantsRepoImpl extends RoleUdtGrantsRepo {
  override def selectAll(implicit c: Connection): List[RoleUdtGrantsRow] = {
    SQL"""select grantor, grantee, udt_catalog, udt_schema, udt_name, privilege_type, is_grantable from information_schema.role_udt_grants""".as(RoleUdtGrantsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[RoleUdtGrantsFieldValue[_]])(implicit c: Connection): List[RoleUdtGrantsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case RoleUdtGrantsFieldValue.grantor(value) => NamedParameter("grantor", ParameterValue.from(value))
          case RoleUdtGrantsFieldValue.grantee(value) => NamedParameter("grantee", ParameterValue.from(value))
          case RoleUdtGrantsFieldValue.udtCatalog(value) => NamedParameter("udt_catalog", ParameterValue.from(value))
          case RoleUdtGrantsFieldValue.udtSchema(value) => NamedParameter("udt_schema", ParameterValue.from(value))
          case RoleUdtGrantsFieldValue.udtName(value) => NamedParameter("udt_name", ParameterValue.from(value))
          case RoleUdtGrantsFieldValue.privilegeType(value) => NamedParameter("privilege_type", ParameterValue.from(value))
          case RoleUdtGrantsFieldValue.isGrantable(value) => NamedParameter("is_grantable", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.role_udt_grants where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(RoleUdtGrantsRow.rowParser.*)
    }

  }
}
