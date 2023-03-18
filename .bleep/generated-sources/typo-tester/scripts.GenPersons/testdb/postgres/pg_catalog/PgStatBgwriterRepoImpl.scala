package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatBgwriterRepoImpl extends PgStatBgwriterRepo {
  override def selectAll(implicit c: Connection): List[PgStatBgwriterRow] = {
    SQL"""select checkpoints_timed, checkpoints_req, checkpoint_write_time, checkpoint_sync_time, buffers_checkpoint, buffers_clean, maxwritten_clean, buffers_backend, buffers_backend_fsync, buffers_alloc, stats_reset from pg_catalog.pg_stat_bgwriter""".as(PgStatBgwriterRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatBgwriterFieldValue[_]])(implicit c: Connection): List[PgStatBgwriterRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatBgwriterFieldValue.checkpointsTimed(value) => NamedParameter("checkpoints_timed", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.checkpointsReq(value) => NamedParameter("checkpoints_req", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.checkpointWriteTime(value) => NamedParameter("checkpoint_write_time", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.checkpointSyncTime(value) => NamedParameter("checkpoint_sync_time", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.buffersCheckpoint(value) => NamedParameter("buffers_checkpoint", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.buffersClean(value) => NamedParameter("buffers_clean", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.maxwrittenClean(value) => NamedParameter("maxwritten_clean", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.buffersBackend(value) => NamedParameter("buffers_backend", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.buffersBackendFsync(value) => NamedParameter("buffers_backend_fsync", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.buffersAlloc(value) => NamedParameter("buffers_alloc", ParameterValue.from(value))
          case PgStatBgwriterFieldValue.statsReset(value) => NamedParameter("stats_reset", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_bgwriter where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatBgwriterRow.rowParser.*)
    }

  }
}
