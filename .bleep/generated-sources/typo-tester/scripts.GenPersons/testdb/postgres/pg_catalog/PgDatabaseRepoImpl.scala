package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgDatabaseRepoImpl extends PgDatabaseRepo {
  override def selectAll(implicit c: Connection): List[PgDatabaseRow] = {
    SQL"""select oid, datname, datdba, encoding, datcollate, datctype, datistemplate, datallowconn, datconnlimit, datlastsysoid, datfrozenxid, datminmxid, dattablespace, datacl from pg_catalog.pg_database""".as(PgDatabaseRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgDatabaseFieldValue[_]])(implicit c: Connection): List[PgDatabaseRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgDatabaseFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgDatabaseFieldValue.datname(value) => NamedParameter("datname", ParameterValue.from(value))
          case PgDatabaseFieldValue.datdba(value) => NamedParameter("datdba", ParameterValue.from(value))
          case PgDatabaseFieldValue.encoding(value) => NamedParameter("encoding", ParameterValue.from(value))
          case PgDatabaseFieldValue.datcollate(value) => NamedParameter("datcollate", ParameterValue.from(value))
          case PgDatabaseFieldValue.datctype(value) => NamedParameter("datctype", ParameterValue.from(value))
          case PgDatabaseFieldValue.datistemplate(value) => NamedParameter("datistemplate", ParameterValue.from(value))
          case PgDatabaseFieldValue.datallowconn(value) => NamedParameter("datallowconn", ParameterValue.from(value))
          case PgDatabaseFieldValue.datconnlimit(value) => NamedParameter("datconnlimit", ParameterValue.from(value))
          case PgDatabaseFieldValue.datlastsysoid(value) => NamedParameter("datlastsysoid", ParameterValue.from(value))
          case PgDatabaseFieldValue.datfrozenxid(value) => NamedParameter("datfrozenxid", ParameterValue.from(value))
          case PgDatabaseFieldValue.datminmxid(value) => NamedParameter("datminmxid", ParameterValue.from(value))
          case PgDatabaseFieldValue.dattablespace(value) => NamedParameter("dattablespace", ParameterValue.from(value))
          case PgDatabaseFieldValue.datacl(value) => NamedParameter("datacl", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_database where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgDatabaseRow.rowParser.*)
    }

  }
}
