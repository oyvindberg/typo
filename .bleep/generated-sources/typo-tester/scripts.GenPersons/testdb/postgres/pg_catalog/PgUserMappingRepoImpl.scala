package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgUserMappingRepoImpl extends PgUserMappingRepo {
  override def selectAll(implicit c: Connection): List[PgUserMappingRow] = {
    SQL"""select oid, umuser, umserver, umoptions from pg_catalog.pg_user_mapping""".as(PgUserMappingRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgUserMappingFieldValue[_]])(implicit c: Connection): List[PgUserMappingRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgUserMappingFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgUserMappingFieldValue.umuser(value) => NamedParameter("umuser", ParameterValue.from(value))
          case PgUserMappingFieldValue.umserver(value) => NamedParameter("umserver", ParameterValue.from(value))
          case PgUserMappingFieldValue.umoptions(value) => NamedParameter("umoptions", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_user_mapping where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgUserMappingRow.rowParser.*)
    }

  }
}
