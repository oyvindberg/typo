package testdb
package postgres
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ConstraintColumnUsageRepoImpl extends ConstraintColumnUsageRepo {
  override def selectAll(implicit c: Connection): List[ConstraintColumnUsageRow] = {
    SQL"""select table_catalog, table_schema, table_name, column_name, constraint_catalog, constraint_schema, constraint_name from information_schema.constraint_column_usage""".as(ConstraintColumnUsageRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ConstraintColumnUsageFieldValue[_]])(implicit c: Connection): List[ConstraintColumnUsageRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ConstraintColumnUsageFieldValue.tableCatalog(value) => NamedParameter("table_catalog", ParameterValue.from(value))
          case ConstraintColumnUsageFieldValue.tableSchema(value) => NamedParameter("table_schema", ParameterValue.from(value))
          case ConstraintColumnUsageFieldValue.tableName(value) => NamedParameter("table_name", ParameterValue.from(value))
          case ConstraintColumnUsageFieldValue.columnName(value) => NamedParameter("column_name", ParameterValue.from(value))
          case ConstraintColumnUsageFieldValue.constraintCatalog(value) => NamedParameter("constraint_catalog", ParameterValue.from(value))
          case ConstraintColumnUsageFieldValue.constraintSchema(value) => NamedParameter("constraint_schema", ParameterValue.from(value))
          case ConstraintColumnUsageFieldValue.constraintName(value) => NamedParameter("constraint_name", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.constraint_column_usage where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ConstraintColumnUsageRow.rowParser.*)
    }

  }
}
