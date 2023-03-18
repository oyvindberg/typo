package testdb
package postgres
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ForeignServersRepoImpl extends ForeignServersRepo {
  override def selectAll(implicit c: Connection): List[ForeignServersRow] = {
    SQL"""select foreign_server_catalog, foreign_server_name, foreign_data_wrapper_catalog, foreign_data_wrapper_name, foreign_server_type, foreign_server_version, authorization_identifier from information_schema.foreign_servers""".as(ForeignServersRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ForeignServersFieldValue[_]])(implicit c: Connection): List[ForeignServersRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ForeignServersFieldValue.foreignServerCatalog(value) => NamedParameter("foreign_server_catalog", ParameterValue.from(value))
          case ForeignServersFieldValue.foreignServerName(value) => NamedParameter("foreign_server_name", ParameterValue.from(value))
          case ForeignServersFieldValue.foreignDataWrapperCatalog(value) => NamedParameter("foreign_data_wrapper_catalog", ParameterValue.from(value))
          case ForeignServersFieldValue.foreignDataWrapperName(value) => NamedParameter("foreign_data_wrapper_name", ParameterValue.from(value))
          case ForeignServersFieldValue.foreignServerType(value) => NamedParameter("foreign_server_type", ParameterValue.from(value))
          case ForeignServersFieldValue.foreignServerVersion(value) => NamedParameter("foreign_server_version", ParameterValue.from(value))
          case ForeignServersFieldValue.authorizationIdentifier(value) => NamedParameter("authorization_identifier", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.foreign_servers where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ForeignServersRow.rowParser.*)
    }

  }
}
