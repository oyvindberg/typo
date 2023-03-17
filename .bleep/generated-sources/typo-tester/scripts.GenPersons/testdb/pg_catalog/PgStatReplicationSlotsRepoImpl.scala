package testdb.pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatReplicationSlotsRepoImpl extends PgStatReplicationSlotsRepo {
  override def selectAll(implicit c: Connection): List[PgStatReplicationSlotsRow] = {
    SQL"""select slot_name, spill_txns, spill_count, spill_bytes, stream_txns, stream_count, stream_bytes, total_txns, total_bytes, stats_reset from pg_catalog.pg_stat_replication_slots""".as(PgStatReplicationSlotsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatReplicationSlotsFieldValue[_]])(implicit c: Connection): List[PgStatReplicationSlotsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatReplicationSlotsFieldValue.slotName(value) => NamedParameter("slot_name", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.spillTxns(value) => NamedParameter("spill_txns", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.spillCount(value) => NamedParameter("spill_count", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.spillBytes(value) => NamedParameter("spill_bytes", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.streamTxns(value) => NamedParameter("stream_txns", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.streamCount(value) => NamedParameter("stream_count", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.streamBytes(value) => NamedParameter("stream_bytes", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.totalTxns(value) => NamedParameter("total_txns", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.totalBytes(value) => NamedParameter("total_bytes", ParameterValue.from(value))
          case PgStatReplicationSlotsFieldValue.statsReset(value) => NamedParameter("stats_reset", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_replication_slots where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatReplicationSlotsRow.rowParser.*)
    }

  }
}
