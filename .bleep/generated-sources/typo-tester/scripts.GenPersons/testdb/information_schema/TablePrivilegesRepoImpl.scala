package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait TablePrivilegesRepoImpl extends TablePrivilegesRepo {
  override def selectAll(implicit c: Connection): List[TablePrivilegesRow] = {
    SQL"""select grantor, grantee, table_catalog, table_schema, table_name, privilege_type, is_grantable, with_hierarchy from information_schema.table_privileges""".as(TablePrivilegesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[TablePrivilegesFieldValue[_]])(implicit c: Connection): List[TablePrivilegesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case TablePrivilegesFieldValue.grantor(value) => NamedParameter("grantor", ParameterValue.from(value))
          case TablePrivilegesFieldValue.grantee(value) => NamedParameter("grantee", ParameterValue.from(value))
          case TablePrivilegesFieldValue.tableCatalog(value) => NamedParameter("table_catalog", ParameterValue.from(value))
          case TablePrivilegesFieldValue.tableSchema(value) => NamedParameter("table_schema", ParameterValue.from(value))
          case TablePrivilegesFieldValue.tableName(value) => NamedParameter("table_name", ParameterValue.from(value))
          case TablePrivilegesFieldValue.privilegeType(value) => NamedParameter("privilege_type", ParameterValue.from(value))
          case TablePrivilegesFieldValue.isGrantable(value) => NamedParameter("is_grantable", ParameterValue.from(value))
          case TablePrivilegesFieldValue.withHierarchy(value) => NamedParameter("with_hierarchy", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.table_privileges where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(TablePrivilegesRow.rowParser.*)
    }

  }
}
