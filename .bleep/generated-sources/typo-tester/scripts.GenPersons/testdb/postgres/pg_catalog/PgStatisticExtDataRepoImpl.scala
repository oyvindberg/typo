package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatisticExtDataRepoImpl extends PgStatisticExtDataRepo {
  override def selectAll(implicit c: Connection): List[PgStatisticExtDataRow] = {
    SQL"""select stxoid, stxdndistinct, stxddependencies, stxdmcv, stxdexpr from pg_catalog.pg_statistic_ext_data""".as(PgStatisticExtDataRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatisticExtDataFieldValue[_]])(implicit c: Connection): List[PgStatisticExtDataRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatisticExtDataFieldValue.stxoid(value) => NamedParameter("stxoid", ParameterValue.from(value))
          case PgStatisticExtDataFieldValue.stxdndistinct(value) => NamedParameter("stxdndistinct", ParameterValue.from(value))
          case PgStatisticExtDataFieldValue.stxddependencies(value) => NamedParameter("stxddependencies", ParameterValue.from(value))
          case PgStatisticExtDataFieldValue.stxdmcv(value) => NamedParameter("stxdmcv", ParameterValue.from(value))
          case PgStatisticExtDataFieldValue.stxdexpr(value) => NamedParameter("stxdexpr", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_statistic_ext_data where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatisticExtDataRow.rowParser.*)
    }

  }
}
