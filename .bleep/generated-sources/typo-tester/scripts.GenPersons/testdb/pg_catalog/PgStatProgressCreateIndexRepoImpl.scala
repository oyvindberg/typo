package testdb
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatProgressCreateIndexRepoImpl extends PgStatProgressCreateIndexRepo {
  override def selectAll(implicit c: Connection): List[PgStatProgressCreateIndexRow] = {
    SQL"""select pid, datid, datname, relid, index_relid, command, phase, lockers_total, lockers_done, current_locker_pid, blocks_total, blocks_done, tuples_total, tuples_done, partitions_total, partitions_done from pg_catalog.pg_stat_progress_create_index""".as(PgStatProgressCreateIndexRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatProgressCreateIndexFieldValue[_]])(implicit c: Connection): List[PgStatProgressCreateIndexRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatProgressCreateIndexFieldValue.pid(value) => NamedParameter("pid", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.datid(value) => NamedParameter("datid", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.datname(value) => NamedParameter("datname", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.relid(value) => NamedParameter("relid", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.indexRelid(value) => NamedParameter("index_relid", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.command(value) => NamedParameter("command", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.phase(value) => NamedParameter("phase", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.lockersTotal(value) => NamedParameter("lockers_total", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.lockersDone(value) => NamedParameter("lockers_done", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.currentLockerPid(value) => NamedParameter("current_locker_pid", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.blocksTotal(value) => NamedParameter("blocks_total", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.blocksDone(value) => NamedParameter("blocks_done", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.tuplesTotal(value) => NamedParameter("tuples_total", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.tuplesDone(value) => NamedParameter("tuples_done", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.partitionsTotal(value) => NamedParameter("partitions_total", ParameterValue.from(value))
          case PgStatProgressCreateIndexFieldValue.partitionsDone(value) => NamedParameter("partitions_done", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_progress_create_index where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatProgressCreateIndexRow.rowParser.*)
    }

  }
}
