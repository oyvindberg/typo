package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatisticExtRepoImpl extends PgStatisticExtRepo {
  override def selectAll(implicit c: Connection): List[PgStatisticExtRow] = {
    SQL"""select oid, stxrelid, stxname, stxnamespace, stxowner, stxstattarget, stxkeys, stxkind, stxexprs from pg_catalog.pg_statistic_ext""".as(PgStatisticExtRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatisticExtFieldValue[_]])(implicit c: Connection): List[PgStatisticExtRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatisticExtFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgStatisticExtFieldValue.stxrelid(value) => NamedParameter("stxrelid", ParameterValue.from(value))
          case PgStatisticExtFieldValue.stxname(value) => NamedParameter("stxname", ParameterValue.from(value))
          case PgStatisticExtFieldValue.stxnamespace(value) => NamedParameter("stxnamespace", ParameterValue.from(value))
          case PgStatisticExtFieldValue.stxowner(value) => NamedParameter("stxowner", ParameterValue.from(value))
          case PgStatisticExtFieldValue.stxstattarget(value) => NamedParameter("stxstattarget", ParameterValue.from(value))
          case PgStatisticExtFieldValue.stxkeys(value) => NamedParameter("stxkeys", ParameterValue.from(value))
          case PgStatisticExtFieldValue.stxkind(value) => NamedParameter("stxkind", ParameterValue.from(value))
          case PgStatisticExtFieldValue.stxexprs(value) => NamedParameter("stxexprs", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_statistic_ext where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatisticExtRow.rowParser.*)
    }

  }
}
