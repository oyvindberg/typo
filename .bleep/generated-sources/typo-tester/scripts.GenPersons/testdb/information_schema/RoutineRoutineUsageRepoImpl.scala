package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait RoutineRoutineUsageRepoImpl extends RoutineRoutineUsageRepo {
  override def selectAll(implicit c: Connection): List[RoutineRoutineUsageRow] = {
    SQL"""select specific_catalog, specific_schema, specific_name, routine_catalog, routine_schema, routine_name from information_schema.routine_routine_usage""".as(RoutineRoutineUsageRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[RoutineRoutineUsageFieldValue[_]])(implicit c: Connection): List[RoutineRoutineUsageRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case RoutineRoutineUsageFieldValue.specificCatalog(value) => NamedParameter("specific_catalog", ParameterValue.from(value))
          case RoutineRoutineUsageFieldValue.specificSchema(value) => NamedParameter("specific_schema", ParameterValue.from(value))
          case RoutineRoutineUsageFieldValue.specificName(value) => NamedParameter("specific_name", ParameterValue.from(value))
          case RoutineRoutineUsageFieldValue.routineCatalog(value) => NamedParameter("routine_catalog", ParameterValue.from(value))
          case RoutineRoutineUsageFieldValue.routineSchema(value) => NamedParameter("routine_schema", ParameterValue.from(value))
          case RoutineRoutineUsageFieldValue.routineName(value) => NamedParameter("routine_name", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.routine_routine_usage where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(RoutineRoutineUsageRow.rowParser.*)
    }

  }
}
