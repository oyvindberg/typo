package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgRangeRepoImpl extends PgRangeRepo {
  override def selectAll(implicit c: Connection): List[PgRangeRow] = {
    SQL"""select rngtypid, rngsubtype, rngmultitypid, rngcollation, rngsubopc, rngcanonical, rngsubdiff from pg_catalog.pg_range""".as(PgRangeRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgRangeFieldValue[_]])(implicit c: Connection): List[PgRangeRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgRangeFieldValue.rngtypid(value) => NamedParameter("rngtypid", ParameterValue.from(value))
          case PgRangeFieldValue.rngsubtype(value) => NamedParameter("rngsubtype", ParameterValue.from(value))
          case PgRangeFieldValue.rngmultitypid(value) => NamedParameter("rngmultitypid", ParameterValue.from(value))
          case PgRangeFieldValue.rngcollation(value) => NamedParameter("rngcollation", ParameterValue.from(value))
          case PgRangeFieldValue.rngsubopc(value) => NamedParameter("rngsubopc", ParameterValue.from(value))
          case PgRangeFieldValue.rngcanonical(value) => NamedParameter("rngcanonical", ParameterValue.from(value))
          case PgRangeFieldValue.rngsubdiff(value) => NamedParameter("rngsubdiff", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_range where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgRangeRow.rowParser.*)
    }

  }
}
