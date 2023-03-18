package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatWalReceiverRepoImpl extends PgStatWalReceiverRepo {
  override def selectAll(implicit c: Connection): List[PgStatWalReceiverRow] = {
    SQL"""select pid, status, receive_start_lsn, receive_start_tli, written_lsn, flushed_lsn, received_tli, last_msg_send_time, last_msg_receipt_time, latest_end_lsn, latest_end_time, slot_name, sender_host, sender_port, conninfo from pg_catalog.pg_stat_wal_receiver""".as(PgStatWalReceiverRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatWalReceiverFieldValue[_]])(implicit c: Connection): List[PgStatWalReceiverRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatWalReceiverFieldValue.pid(value) => NamedParameter("pid", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.status(value) => NamedParameter("status", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.receiveStartLsn(value) => NamedParameter("receive_start_lsn", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.receiveStartTli(value) => NamedParameter("receive_start_tli", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.writtenLsn(value) => NamedParameter("written_lsn", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.flushedLsn(value) => NamedParameter("flushed_lsn", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.receivedTli(value) => NamedParameter("received_tli", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.lastMsgSendTime(value) => NamedParameter("last_msg_send_time", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.lastMsgReceiptTime(value) => NamedParameter("last_msg_receipt_time", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.latestEndLsn(value) => NamedParameter("latest_end_lsn", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.latestEndTime(value) => NamedParameter("latest_end_time", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.slotName(value) => NamedParameter("slot_name", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.senderHost(value) => NamedParameter("sender_host", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.senderPort(value) => NamedParameter("sender_port", ParameterValue.from(value))
          case PgStatWalReceiverFieldValue.conninfo(value) => NamedParameter("conninfo", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_wal_receiver where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatWalReceiverRow.rowParser.*)
    }

  }
}
