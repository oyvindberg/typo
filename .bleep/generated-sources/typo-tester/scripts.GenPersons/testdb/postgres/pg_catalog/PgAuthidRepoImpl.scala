package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgAuthidRepoImpl extends PgAuthidRepo {
  override def selectAll(implicit c: Connection): List[PgAuthidRow] = {
    SQL"""select oid, rolname, rolsuper, rolinherit, rolcreaterole, rolcreatedb, rolcanlogin, rolreplication, rolbypassrls, rolconnlimit, rolpassword, rolvaliduntil from pg_catalog.pg_authid""".as(PgAuthidRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAuthidFieldValue[_]])(implicit c: Connection): List[PgAuthidRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAuthidFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgAuthidFieldValue.rolname(value) => NamedParameter("rolname", ParameterValue.from(value))
          case PgAuthidFieldValue.rolsuper(value) => NamedParameter("rolsuper", ParameterValue.from(value))
          case PgAuthidFieldValue.rolinherit(value) => NamedParameter("rolinherit", ParameterValue.from(value))
          case PgAuthidFieldValue.rolcreaterole(value) => NamedParameter("rolcreaterole", ParameterValue.from(value))
          case PgAuthidFieldValue.rolcreatedb(value) => NamedParameter("rolcreatedb", ParameterValue.from(value))
          case PgAuthidFieldValue.rolcanlogin(value) => NamedParameter("rolcanlogin", ParameterValue.from(value))
          case PgAuthidFieldValue.rolreplication(value) => NamedParameter("rolreplication", ParameterValue.from(value))
          case PgAuthidFieldValue.rolbypassrls(value) => NamedParameter("rolbypassrls", ParameterValue.from(value))
          case PgAuthidFieldValue.rolconnlimit(value) => NamedParameter("rolconnlimit", ParameterValue.from(value))
          case PgAuthidFieldValue.rolpassword(value) => NamedParameter("rolpassword", ParameterValue.from(value))
          case PgAuthidFieldValue.rolvaliduntil(value) => NamedParameter("rolvaliduntil", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_authid where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgAuthidRow.rowParser.*)
    }

  }
}
