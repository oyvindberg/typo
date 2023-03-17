package testdb.pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatProgressBasebackupRepoImpl extends PgStatProgressBasebackupRepo {
  override def selectAll(implicit c: Connection): List[PgStatProgressBasebackupRow] = {
    SQL"""select pid, phase, backup_total, backup_streamed, tablespaces_total, tablespaces_streamed from pg_catalog.pg_stat_progress_basebackup""".as(PgStatProgressBasebackupRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatProgressBasebackupFieldValue[_]])(implicit c: Connection): List[PgStatProgressBasebackupRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatProgressBasebackupFieldValue.pid(value) => NamedParameter("pid", ParameterValue.from(value))
          case PgStatProgressBasebackupFieldValue.phase(value) => NamedParameter("phase", ParameterValue.from(value))
          case PgStatProgressBasebackupFieldValue.backupTotal(value) => NamedParameter("backup_total", ParameterValue.from(value))
          case PgStatProgressBasebackupFieldValue.backupStreamed(value) => NamedParameter("backup_streamed", ParameterValue.from(value))
          case PgStatProgressBasebackupFieldValue.tablespacesTotal(value) => NamedParameter("tablespaces_total", ParameterValue.from(value))
          case PgStatProgressBasebackupFieldValue.tablespacesStreamed(value) => NamedParameter("tablespaces_streamed", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_progress_basebackup where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatProgressBasebackupRow.rowParser.*)
    }

  }
}
