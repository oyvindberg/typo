package testdb
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgPreparedStatementsRepoImpl extends PgPreparedStatementsRepo {
  override def selectAll(implicit c: Connection): List[PgPreparedStatementsRow] = {
    SQL"""select name, statement, prepare_time, parameter_types, from_sql, generic_plans, custom_plans from pg_catalog.pg_prepared_statements""".as(PgPreparedStatementsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgPreparedStatementsFieldValue[_]])(implicit c: Connection): List[PgPreparedStatementsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgPreparedStatementsFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case PgPreparedStatementsFieldValue.statement(value) => NamedParameter("statement", ParameterValue.from(value))
          case PgPreparedStatementsFieldValue.prepareTime(value) => NamedParameter("prepare_time", ParameterValue.from(value))
          case PgPreparedStatementsFieldValue.parameterTypes(value) => NamedParameter("parameter_types", ParameterValue.from(value))
          case PgPreparedStatementsFieldValue.fromSql(value) => NamedParameter("from_sql", ParameterValue.from(value))
          case PgPreparedStatementsFieldValue.genericPlans(value) => NamedParameter("generic_plans", ParameterValue.from(value))
          case PgPreparedStatementsFieldValue.customPlans(value) => NamedParameter("custom_plans", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_prepared_statements where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgPreparedStatementsRow.rowParser.*)
    }

  }
}
