package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgForeignServerRepoImpl extends PgForeignServerRepo {
  override def selectAll(implicit c: Connection): List[PgForeignServerRow] = {
    SQL"""select oid, srvname, srvowner, srvfdw, srvtype, srvversion, srvacl, srvoptions from pg_catalog.pg_foreign_server""".as(PgForeignServerRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgForeignServerFieldValue[_]])(implicit c: Connection): List[PgForeignServerRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgForeignServerFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgForeignServerFieldValue.srvname(value) => NamedParameter("srvname", ParameterValue.from(value))
          case PgForeignServerFieldValue.srvowner(value) => NamedParameter("srvowner", ParameterValue.from(value))
          case PgForeignServerFieldValue.srvfdw(value) => NamedParameter("srvfdw", ParameterValue.from(value))
          case PgForeignServerFieldValue.srvtype(value) => NamedParameter("srvtype", ParameterValue.from(value))
          case PgForeignServerFieldValue.srvversion(value) => NamedParameter("srvversion", ParameterValue.from(value))
          case PgForeignServerFieldValue.srvacl(value) => NamedParameter("srvacl", ParameterValue.from(value))
          case PgForeignServerFieldValue.srvoptions(value) => NamedParameter("srvoptions", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_foreign_server where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgForeignServerRow.rowParser.*)
    }

  }
}
