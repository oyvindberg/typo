package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTsDictRepoImpl extends PgTsDictRepo {
  override def selectAll(implicit c: Connection): List[PgTsDictRow] = {
    SQL"""select oid, dictname, dictnamespace, dictowner, dicttemplate, dictinitoption from pg_catalog.pg_ts_dict""".as(PgTsDictRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTsDictFieldValue[_]])(implicit c: Connection): List[PgTsDictRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTsDictFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgTsDictFieldValue.dictname(value) => NamedParameter("dictname", ParameterValue.from(value))
          case PgTsDictFieldValue.dictnamespace(value) => NamedParameter("dictnamespace", ParameterValue.from(value))
          case PgTsDictFieldValue.dictowner(value) => NamedParameter("dictowner", ParameterValue.from(value))
          case PgTsDictFieldValue.dicttemplate(value) => NamedParameter("dicttemplate", ParameterValue.from(value))
          case PgTsDictFieldValue.dictinitoption(value) => NamedParameter("dictinitoption", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_ts_dict where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTsDictRow.rowParser.*)
    }

  }
}
