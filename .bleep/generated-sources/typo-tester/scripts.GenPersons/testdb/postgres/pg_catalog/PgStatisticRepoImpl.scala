package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatisticRepoImpl extends PgStatisticRepo {
  override def selectAll(implicit c: Connection): List[PgStatisticRow] = {
    SQL"""select starelid, staattnum, stainherit, stanullfrac, stawidth, stadistinct, stakind1, stakind2, stakind3, stakind4, stakind5, staop1, staop2, staop3, staop4, staop5, stacoll1, stacoll2, stacoll3, stacoll4, stacoll5, stanumbers1, stanumbers2, stanumbers3, stanumbers4, stanumbers5, stavalues1, stavalues2, stavalues3, stavalues4, stavalues5 from pg_catalog.pg_statistic""".as(PgStatisticRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatisticFieldValue[_]])(implicit c: Connection): List[PgStatisticRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatisticFieldValue.starelid(value) => NamedParameter("starelid", ParameterValue.from(value))
          case PgStatisticFieldValue.staattnum(value) => NamedParameter("staattnum", ParameterValue.from(value))
          case PgStatisticFieldValue.stainherit(value) => NamedParameter("stainherit", ParameterValue.from(value))
          case PgStatisticFieldValue.stanullfrac(value) => NamedParameter("stanullfrac", ParameterValue.from(value))
          case PgStatisticFieldValue.stawidth(value) => NamedParameter("stawidth", ParameterValue.from(value))
          case PgStatisticFieldValue.stadistinct(value) => NamedParameter("stadistinct", ParameterValue.from(value))
          case PgStatisticFieldValue.stakind1(value) => NamedParameter("stakind1", ParameterValue.from(value))
          case PgStatisticFieldValue.stakind2(value) => NamedParameter("stakind2", ParameterValue.from(value))
          case PgStatisticFieldValue.stakind3(value) => NamedParameter("stakind3", ParameterValue.from(value))
          case PgStatisticFieldValue.stakind4(value) => NamedParameter("stakind4", ParameterValue.from(value))
          case PgStatisticFieldValue.stakind5(value) => NamedParameter("stakind5", ParameterValue.from(value))
          case PgStatisticFieldValue.staop1(value) => NamedParameter("staop1", ParameterValue.from(value))
          case PgStatisticFieldValue.staop2(value) => NamedParameter("staop2", ParameterValue.from(value))
          case PgStatisticFieldValue.staop3(value) => NamedParameter("staop3", ParameterValue.from(value))
          case PgStatisticFieldValue.staop4(value) => NamedParameter("staop4", ParameterValue.from(value))
          case PgStatisticFieldValue.staop5(value) => NamedParameter("staop5", ParameterValue.from(value))
          case PgStatisticFieldValue.stacoll1(value) => NamedParameter("stacoll1", ParameterValue.from(value))
          case PgStatisticFieldValue.stacoll2(value) => NamedParameter("stacoll2", ParameterValue.from(value))
          case PgStatisticFieldValue.stacoll3(value) => NamedParameter("stacoll3", ParameterValue.from(value))
          case PgStatisticFieldValue.stacoll4(value) => NamedParameter("stacoll4", ParameterValue.from(value))
          case PgStatisticFieldValue.stacoll5(value) => NamedParameter("stacoll5", ParameterValue.from(value))
          case PgStatisticFieldValue.stanumbers1(value) => NamedParameter("stanumbers1", ParameterValue.from(value))
          case PgStatisticFieldValue.stanumbers2(value) => NamedParameter("stanumbers2", ParameterValue.from(value))
          case PgStatisticFieldValue.stanumbers3(value) => NamedParameter("stanumbers3", ParameterValue.from(value))
          case PgStatisticFieldValue.stanumbers4(value) => NamedParameter("stanumbers4", ParameterValue.from(value))
          case PgStatisticFieldValue.stanumbers5(value) => NamedParameter("stanumbers5", ParameterValue.from(value))
          case PgStatisticFieldValue.stavalues1(value) => NamedParameter("stavalues1", ParameterValue.from(value))
          case PgStatisticFieldValue.stavalues2(value) => NamedParameter("stavalues2", ParameterValue.from(value))
          case PgStatisticFieldValue.stavalues3(value) => NamedParameter("stavalues3", ParameterValue.from(value))
          case PgStatisticFieldValue.stavalues4(value) => NamedParameter("stavalues4", ParameterValue.from(value))
          case PgStatisticFieldValue.stavalues5(value) => NamedParameter("stavalues5", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_statistic where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatisticRow.rowParser.*)
    }

  }
}
