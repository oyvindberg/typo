package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatXactUserFunctionsRepoImpl extends PgStatXactUserFunctionsRepo {
  override def selectAll(implicit c: Connection): List[PgStatXactUserFunctionsRow] = {
    SQL"""select funcid, schemaname, funcname, calls, total_time, self_time from pg_catalog.pg_stat_xact_user_functions""".as(PgStatXactUserFunctionsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatXactUserFunctionsFieldValue[_]])(implicit c: Connection): List[PgStatXactUserFunctionsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatXactUserFunctionsFieldValue.funcid(value) => NamedParameter("funcid", ParameterValue.from(value))
          case PgStatXactUserFunctionsFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgStatXactUserFunctionsFieldValue.funcname(value) => NamedParameter("funcname", ParameterValue.from(value))
          case PgStatXactUserFunctionsFieldValue.calls(value) => NamedParameter("calls", ParameterValue.from(value))
          case PgStatXactUserFunctionsFieldValue.totalTime(value) => NamedParameter("total_time", ParameterValue.from(value))
          case PgStatXactUserFunctionsFieldValue.selfTime(value) => NamedParameter("self_time", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_xact_user_functions where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatXactUserFunctionsRow.rowParser.*)
    }

  }
}
