package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatXactUserTablesRepoImpl extends PgStatXactUserTablesRepo {
  override def selectAll(implicit c: Connection): List[PgStatXactUserTablesRow] = {
    SQL"""select relid, schemaname, relname, seq_scan, seq_tup_read, idx_scan, idx_tup_fetch, n_tup_ins, n_tup_upd, n_tup_del, n_tup_hot_upd from pg_catalog.pg_stat_xact_user_tables""".as(PgStatXactUserTablesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatXactUserTablesFieldValue[_]])(implicit c: Connection): List[PgStatXactUserTablesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatXactUserTablesFieldValue.relid(value) => NamedParameter("relid", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.relname(value) => NamedParameter("relname", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.seqScan(value) => NamedParameter("seq_scan", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.seqTupRead(value) => NamedParameter("seq_tup_read", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.idxScan(value) => NamedParameter("idx_scan", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.idxTupFetch(value) => NamedParameter("idx_tup_fetch", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.nTupIns(value) => NamedParameter("n_tup_ins", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.nTupUpd(value) => NamedParameter("n_tup_upd", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.nTupDel(value) => NamedParameter("n_tup_del", ParameterValue.from(value))
          case PgStatXactUserTablesFieldValue.nTupHotUpd(value) => NamedParameter("n_tup_hot_upd", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_xact_user_tables where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatXactUserTablesRow.rowParser.*)
    }

  }
}
