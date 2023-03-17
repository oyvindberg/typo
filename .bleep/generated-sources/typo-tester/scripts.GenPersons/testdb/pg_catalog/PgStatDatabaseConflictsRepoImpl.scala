package testdb
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatDatabaseConflictsRepoImpl extends PgStatDatabaseConflictsRepo {
  override def selectAll(implicit c: Connection): List[PgStatDatabaseConflictsRow] = {
    SQL"""select datid, datname, confl_tablespace, confl_lock, confl_snapshot, confl_bufferpin, confl_deadlock from pg_catalog.pg_stat_database_conflicts""".as(PgStatDatabaseConflictsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatDatabaseConflictsFieldValue[_]])(implicit c: Connection): List[PgStatDatabaseConflictsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatDatabaseConflictsFieldValue.datid(value) => NamedParameter("datid", ParameterValue.from(value))
          case PgStatDatabaseConflictsFieldValue.datname(value) => NamedParameter("datname", ParameterValue.from(value))
          case PgStatDatabaseConflictsFieldValue.conflTablespace(value) => NamedParameter("confl_tablespace", ParameterValue.from(value))
          case PgStatDatabaseConflictsFieldValue.conflLock(value) => NamedParameter("confl_lock", ParameterValue.from(value))
          case PgStatDatabaseConflictsFieldValue.conflSnapshot(value) => NamedParameter("confl_snapshot", ParameterValue.from(value))
          case PgStatDatabaseConflictsFieldValue.conflBufferpin(value) => NamedParameter("confl_bufferpin", ParameterValue.from(value))
          case PgStatDatabaseConflictsFieldValue.conflDeadlock(value) => NamedParameter("confl_deadlock", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_database_conflicts where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatDatabaseConflictsRow.rowParser.*)
    }

  }
}
