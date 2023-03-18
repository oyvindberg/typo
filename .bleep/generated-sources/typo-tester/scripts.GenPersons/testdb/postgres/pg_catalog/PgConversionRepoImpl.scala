package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgConversionRepoImpl extends PgConversionRepo {
  override def selectAll(implicit c: Connection): List[PgConversionRow] = {
    SQL"""select oid, conname, connamespace, conowner, conforencoding, contoencoding, conproc, condefault from pg_catalog.pg_conversion""".as(PgConversionRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgConversionFieldValue[_]])(implicit c: Connection): List[PgConversionRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgConversionFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgConversionFieldValue.conname(value) => NamedParameter("conname", ParameterValue.from(value))
          case PgConversionFieldValue.connamespace(value) => NamedParameter("connamespace", ParameterValue.from(value))
          case PgConversionFieldValue.conowner(value) => NamedParameter("conowner", ParameterValue.from(value))
          case PgConversionFieldValue.conforencoding(value) => NamedParameter("conforencoding", ParameterValue.from(value))
          case PgConversionFieldValue.contoencoding(value) => NamedParameter("contoencoding", ParameterValue.from(value))
          case PgConversionFieldValue.conproc(value) => NamedParameter("conproc", ParameterValue.from(value))
          case PgConversionFieldValue.condefault(value) => NamedParameter("condefault", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_conversion where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgConversionRow.rowParser.*)
    }

  }
}
