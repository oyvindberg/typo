package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ColumnPrivilegesRepoImpl extends ColumnPrivilegesRepo {
  override def selectAll(implicit c: Connection): List[ColumnPrivilegesRow] = {
    SQL"""select grantor, grantee, table_catalog, table_schema, table_name, column_name, privilege_type, is_grantable from information_schema.column_privileges""".as(ColumnPrivilegesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ColumnPrivilegesFieldValue[_]])(implicit c: Connection): List[ColumnPrivilegesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ColumnPrivilegesFieldValue.grantor(value) => NamedParameter("grantor", ParameterValue.from(value))
          case ColumnPrivilegesFieldValue.grantee(value) => NamedParameter("grantee", ParameterValue.from(value))
          case ColumnPrivilegesFieldValue.tableCatalog(value) => NamedParameter("table_catalog", ParameterValue.from(value))
          case ColumnPrivilegesFieldValue.tableSchema(value) => NamedParameter("table_schema", ParameterValue.from(value))
          case ColumnPrivilegesFieldValue.tableName(value) => NamedParameter("table_name", ParameterValue.from(value))
          case ColumnPrivilegesFieldValue.columnName(value) => NamedParameter("column_name", ParameterValue.from(value))
          case ColumnPrivilegesFieldValue.privilegeType(value) => NamedParameter("privilege_type", ParameterValue.from(value))
          case ColumnPrivilegesFieldValue.isGrantable(value) => NamedParameter("is_grantable", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.column_privileges where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ColumnPrivilegesRow.rowParser.*)
    }

  }
}
