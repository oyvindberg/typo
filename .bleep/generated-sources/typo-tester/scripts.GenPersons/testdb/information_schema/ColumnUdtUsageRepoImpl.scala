package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ColumnUdtUsageRepoImpl extends ColumnUdtUsageRepo {
  override def selectAll(implicit c: Connection): List[ColumnUdtUsageRow] = {
    SQL"""select udt_catalog, udt_schema, udt_name, table_catalog, table_schema, table_name, column_name from information_schema.column_udt_usage""".as(ColumnUdtUsageRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ColumnUdtUsageFieldValue[_]])(implicit c: Connection): List[ColumnUdtUsageRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ColumnUdtUsageFieldValue.udtCatalog(value) => NamedParameter("udt_catalog", ParameterValue.from(value))
          case ColumnUdtUsageFieldValue.udtSchema(value) => NamedParameter("udt_schema", ParameterValue.from(value))
          case ColumnUdtUsageFieldValue.udtName(value) => NamedParameter("udt_name", ParameterValue.from(value))
          case ColumnUdtUsageFieldValue.tableCatalog(value) => NamedParameter("table_catalog", ParameterValue.from(value))
          case ColumnUdtUsageFieldValue.tableSchema(value) => NamedParameter("table_schema", ParameterValue.from(value))
          case ColumnUdtUsageFieldValue.tableName(value) => NamedParameter("table_name", ParameterValue.from(value))
          case ColumnUdtUsageFieldValue.columnName(value) => NamedParameter("column_name", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.column_udt_usage where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ColumnUdtUsageRow.rowParser.*)
    }

  }
}
