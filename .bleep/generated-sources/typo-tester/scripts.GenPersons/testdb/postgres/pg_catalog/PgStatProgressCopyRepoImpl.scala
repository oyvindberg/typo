package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatProgressCopyRepoImpl extends PgStatProgressCopyRepo {
  override def selectAll(implicit c: Connection): List[PgStatProgressCopyRow] = {
    SQL"""select pid, datid, datname, relid, command, type, bytes_processed, bytes_total, tuples_processed, tuples_excluded from pg_catalog.pg_stat_progress_copy""".as(PgStatProgressCopyRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatProgressCopyFieldValue[_]])(implicit c: Connection): List[PgStatProgressCopyRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatProgressCopyFieldValue.pid(value) => NamedParameter("pid", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.datid(value) => NamedParameter("datid", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.datname(value) => NamedParameter("datname", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.relid(value) => NamedParameter("relid", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.command(value) => NamedParameter("command", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.`type`(value) => NamedParameter("type", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.bytesProcessed(value) => NamedParameter("bytes_processed", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.bytesTotal(value) => NamedParameter("bytes_total", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.tuplesProcessed(value) => NamedParameter("tuples_processed", ParameterValue.from(value))
          case PgStatProgressCopyFieldValue.tuplesExcluded(value) => NamedParameter("tuples_excluded", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_progress_copy where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatProgressCopyRow.rowParser.*)
    }

  }
}
