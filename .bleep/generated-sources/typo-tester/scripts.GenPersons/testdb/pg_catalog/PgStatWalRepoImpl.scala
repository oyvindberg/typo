package testdb
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatWalRepoImpl extends PgStatWalRepo {
  override def selectAll(implicit c: Connection): List[PgStatWalRow] = {
    SQL"""select wal_records, wal_fpi, wal_bytes, wal_buffers_full, wal_write, wal_sync, wal_write_time, wal_sync_time, stats_reset from pg_catalog.pg_stat_wal""".as(PgStatWalRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatWalFieldValue[_]])(implicit c: Connection): List[PgStatWalRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatWalFieldValue.walRecords(value) => NamedParameter("wal_records", ParameterValue.from(value))
          case PgStatWalFieldValue.walFpi(value) => NamedParameter("wal_fpi", ParameterValue.from(value))
          case PgStatWalFieldValue.walBytes(value) => NamedParameter("wal_bytes", ParameterValue.from(value))
          case PgStatWalFieldValue.walBuffersFull(value) => NamedParameter("wal_buffers_full", ParameterValue.from(value))
          case PgStatWalFieldValue.walWrite(value) => NamedParameter("wal_write", ParameterValue.from(value))
          case PgStatWalFieldValue.walSync(value) => NamedParameter("wal_sync", ParameterValue.from(value))
          case PgStatWalFieldValue.walWriteTime(value) => NamedParameter("wal_write_time", ParameterValue.from(value))
          case PgStatWalFieldValue.walSyncTime(value) => NamedParameter("wal_sync_time", ParameterValue.from(value))
          case PgStatWalFieldValue.statsReset(value) => NamedParameter("stats_reset", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_wal where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatWalRow.rowParser.*)
    }

  }
}
