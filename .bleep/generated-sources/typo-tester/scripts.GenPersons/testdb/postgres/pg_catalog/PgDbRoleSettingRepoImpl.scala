package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgDbRoleSettingRepoImpl extends PgDbRoleSettingRepo {
  override def selectAll(implicit c: Connection): List[PgDbRoleSettingRow] = {
    SQL"""select setdatabase, setrole, setconfig from pg_catalog.pg_db_role_setting""".as(PgDbRoleSettingRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgDbRoleSettingFieldValue[_]])(implicit c: Connection): List[PgDbRoleSettingRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgDbRoleSettingFieldValue.setdatabase(value) => NamedParameter("setdatabase", ParameterValue.from(value))
          case PgDbRoleSettingFieldValue.setrole(value) => NamedParameter("setrole", ParameterValue.from(value))
          case PgDbRoleSettingFieldValue.setconfig(value) => NamedParameter("setconfig", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_db_role_setting where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgDbRoleSettingRow.rowParser.*)
    }

  }
}
