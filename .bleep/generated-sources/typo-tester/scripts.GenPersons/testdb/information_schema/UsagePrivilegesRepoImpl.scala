package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait UsagePrivilegesRepoImpl extends UsagePrivilegesRepo {
  override def selectAll(implicit c: Connection): List[UsagePrivilegesRow] = {
    SQL"""select grantor, grantee, object_catalog, object_schema, object_name, object_type, privilege_type, is_grantable from information_schema.usage_privileges""".as(UsagePrivilegesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[UsagePrivilegesFieldValue[_]])(implicit c: Connection): List[UsagePrivilegesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case UsagePrivilegesFieldValue.grantor(value) => NamedParameter("grantor", ParameterValue.from(value))
          case UsagePrivilegesFieldValue.grantee(value) => NamedParameter("grantee", ParameterValue.from(value))
          case UsagePrivilegesFieldValue.objectCatalog(value) => NamedParameter("object_catalog", ParameterValue.from(value))
          case UsagePrivilegesFieldValue.objectSchema(value) => NamedParameter("object_schema", ParameterValue.from(value))
          case UsagePrivilegesFieldValue.objectName(value) => NamedParameter("object_name", ParameterValue.from(value))
          case UsagePrivilegesFieldValue.objectType(value) => NamedParameter("object_type", ParameterValue.from(value))
          case UsagePrivilegesFieldValue.privilegeType(value) => NamedParameter("privilege_type", ParameterValue.from(value))
          case UsagePrivilegesFieldValue.isGrantable(value) => NamedParameter("is_grantable", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.usage_privileges where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(UsagePrivilegesRow.rowParser.*)
    }

  }
}
