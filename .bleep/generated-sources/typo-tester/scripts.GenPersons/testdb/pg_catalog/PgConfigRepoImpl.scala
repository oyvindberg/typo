package testdb.pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgConfigRepoImpl extends PgConfigRepo {
  override def selectAll(implicit c: Connection): List[PgConfigRow] = {
    SQL"""select name, setting from pg_catalog.pg_config""".as(PgConfigRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgConfigFieldValue[_]])(implicit c: Connection): List[PgConfigRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgConfigFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case PgConfigFieldValue.setting(value) => NamedParameter("setting", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_config where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgConfigRow.rowParser.*)
    }

  }
}
