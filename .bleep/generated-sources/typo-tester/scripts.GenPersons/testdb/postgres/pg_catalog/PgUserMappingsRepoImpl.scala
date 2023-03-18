package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgUserMappingsRepoImpl extends PgUserMappingsRepo {
  override def selectAll(implicit c: Connection): List[PgUserMappingsRow] = {
    SQL"""select umid, srvid, srvname, umuser, usename, umoptions from pg_catalog.pg_user_mappings""".as(PgUserMappingsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgUserMappingsFieldValue[_]])(implicit c: Connection): List[PgUserMappingsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgUserMappingsFieldValue.umid(value) => NamedParameter("umid", ParameterValue.from(value))
          case PgUserMappingsFieldValue.srvid(value) => NamedParameter("srvid", ParameterValue.from(value))
          case PgUserMappingsFieldValue.srvname(value) => NamedParameter("srvname", ParameterValue.from(value))
          case PgUserMappingsFieldValue.umuser(value) => NamedParameter("umuser", ParameterValue.from(value))
          case PgUserMappingsFieldValue.usename(value) => NamedParameter("usename", ParameterValue.from(value))
          case PgUserMappingsFieldValue.umoptions(value) => NamedParameter("umoptions", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_user_mappings where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgUserMappingsRow.rowParser.*)
    }

  }
}
