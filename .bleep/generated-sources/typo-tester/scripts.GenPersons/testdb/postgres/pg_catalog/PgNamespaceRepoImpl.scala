package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgNamespaceRepoImpl extends PgNamespaceRepo {
  override def selectAll(implicit c: Connection): List[PgNamespaceRow] = {
    SQL"""select oid, nspname, nspowner, nspacl from pg_catalog.pg_namespace""".as(PgNamespaceRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgNamespaceFieldValue[_]])(implicit c: Connection): List[PgNamespaceRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgNamespaceFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgNamespaceFieldValue.nspname(value) => NamedParameter("nspname", ParameterValue.from(value))
          case PgNamespaceFieldValue.nspowner(value) => NamedParameter("nspowner", ParameterValue.from(value))
          case PgNamespaceFieldValue.nspacl(value) => NamedParameter("nspacl", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_namespace where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgNamespaceRow.rowParser.*)
    }

  }
}
