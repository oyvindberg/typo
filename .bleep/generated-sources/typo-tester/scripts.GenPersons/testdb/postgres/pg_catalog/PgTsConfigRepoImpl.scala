package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTsConfigRepoImpl extends PgTsConfigRepo {
  override def selectAll(implicit c: Connection): List[PgTsConfigRow] = {
    SQL"""select oid, cfgname, cfgnamespace, cfgowner, cfgparser from pg_catalog.pg_ts_config""".as(PgTsConfigRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTsConfigFieldValue[_]])(implicit c: Connection): List[PgTsConfigRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTsConfigFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgTsConfigFieldValue.cfgname(value) => NamedParameter("cfgname", ParameterValue.from(value))
          case PgTsConfigFieldValue.cfgnamespace(value) => NamedParameter("cfgnamespace", ParameterValue.from(value))
          case PgTsConfigFieldValue.cfgowner(value) => NamedParameter("cfgowner", ParameterValue.from(value))
          case PgTsConfigFieldValue.cfgparser(value) => NamedParameter("cfgparser", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_ts_config where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTsConfigRow.rowParser.*)
    }

  }
}
