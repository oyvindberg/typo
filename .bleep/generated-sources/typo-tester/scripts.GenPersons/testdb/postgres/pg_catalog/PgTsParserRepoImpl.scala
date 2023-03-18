package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTsParserRepoImpl extends PgTsParserRepo {
  override def selectAll(implicit c: Connection): List[PgTsParserRow] = {
    SQL"""select oid, prsname, prsnamespace, prsstart, prstoken, prsend, prsheadline, prslextype from pg_catalog.pg_ts_parser""".as(PgTsParserRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTsParserFieldValue[_]])(implicit c: Connection): List[PgTsParserRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTsParserFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgTsParserFieldValue.prsname(value) => NamedParameter("prsname", ParameterValue.from(value))
          case PgTsParserFieldValue.prsnamespace(value) => NamedParameter("prsnamespace", ParameterValue.from(value))
          case PgTsParserFieldValue.prsstart(value) => NamedParameter("prsstart", ParameterValue.from(value))
          case PgTsParserFieldValue.prstoken(value) => NamedParameter("prstoken", ParameterValue.from(value))
          case PgTsParserFieldValue.prsend(value) => NamedParameter("prsend", ParameterValue.from(value))
          case PgTsParserFieldValue.prsheadline(value) => NamedParameter("prsheadline", ParameterValue.from(value))
          case PgTsParserFieldValue.prslextype(value) => NamedParameter("prslextype", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_ts_parser where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTsParserRow.rowParser.*)
    }

  }
}
