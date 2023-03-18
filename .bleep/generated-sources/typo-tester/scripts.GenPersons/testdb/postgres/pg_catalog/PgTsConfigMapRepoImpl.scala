package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTsConfigMapRepoImpl extends PgTsConfigMapRepo {
  override def selectAll(implicit c: Connection): List[PgTsConfigMapRow] = {
    SQL"""select mapcfg, maptokentype, mapseqno, mapdict from pg_catalog.pg_ts_config_map""".as(PgTsConfigMapRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTsConfigMapFieldValue[_]])(implicit c: Connection): List[PgTsConfigMapRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTsConfigMapFieldValue.mapcfg(value) => NamedParameter("mapcfg", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.maptokentype(value) => NamedParameter("maptokentype", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.mapseqno(value) => NamedParameter("mapseqno", ParameterValue.from(value))
          case PgTsConfigMapFieldValue.mapdict(value) => NamedParameter("mapdict", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_ts_config_map where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTsConfigMapRow.rowParser.*)
    }

  }
}
