package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ConstraintTableUsageRepoImpl extends ConstraintTableUsageRepo {
  override def selectAll(implicit c: Connection): List[ConstraintTableUsageRow] = {
    SQL"""select table_catalog, table_schema, table_name, constraint_catalog, constraint_schema, constraint_name from information_schema.constraint_table_usage""".as(ConstraintTableUsageRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ConstraintTableUsageFieldValue[_]])(implicit c: Connection): List[ConstraintTableUsageRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ConstraintTableUsageFieldValue.tableCatalog(value) => NamedParameter("table_catalog", ParameterValue.from(value))
          case ConstraintTableUsageFieldValue.tableSchema(value) => NamedParameter("table_schema", ParameterValue.from(value))
          case ConstraintTableUsageFieldValue.tableName(value) => NamedParameter("table_name", ParameterValue.from(value))
          case ConstraintTableUsageFieldValue.constraintCatalog(value) => NamedParameter("constraint_catalog", ParameterValue.from(value))
          case ConstraintTableUsageFieldValue.constraintSchema(value) => NamedParameter("constraint_schema", ParameterValue.from(value))
          case ConstraintTableUsageFieldValue.constraintName(value) => NamedParameter("constraint_name", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.constraint_table_usage where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ConstraintTableUsageRow.rowParser.*)
    }

  }
}
