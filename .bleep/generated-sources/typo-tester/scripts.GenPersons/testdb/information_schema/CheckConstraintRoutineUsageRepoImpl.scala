package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait CheckConstraintRoutineUsageRepoImpl extends CheckConstraintRoutineUsageRepo {
  override def selectAll(implicit c: Connection): List[CheckConstraintRoutineUsageRow] = {
    SQL"""select constraint_catalog, constraint_schema, constraint_name, specific_catalog, specific_schema, specific_name from information_schema.check_constraint_routine_usage""".as(CheckConstraintRoutineUsageRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[CheckConstraintRoutineUsageFieldValue[_]])(implicit c: Connection): List[CheckConstraintRoutineUsageRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case CheckConstraintRoutineUsageFieldValue.constraintCatalog(value) => NamedParameter("constraint_catalog", ParameterValue.from(value))
          case CheckConstraintRoutineUsageFieldValue.constraintSchema(value) => NamedParameter("constraint_schema", ParameterValue.from(value))
          case CheckConstraintRoutineUsageFieldValue.constraintName(value) => NamedParameter("constraint_name", ParameterValue.from(value))
          case CheckConstraintRoutineUsageFieldValue.specificCatalog(value) => NamedParameter("specific_catalog", ParameterValue.from(value))
          case CheckConstraintRoutineUsageFieldValue.specificSchema(value) => NamedParameter("specific_schema", ParameterValue.from(value))
          case CheckConstraintRoutineUsageFieldValue.specificName(value) => NamedParameter("specific_name", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.check_constraint_routine_usage where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(CheckConstraintRoutineUsageRow.rowParser.*)
    }

  }
}
