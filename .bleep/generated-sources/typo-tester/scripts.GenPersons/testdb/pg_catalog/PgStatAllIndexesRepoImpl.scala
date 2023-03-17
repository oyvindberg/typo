package testdb
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatAllIndexesRepoImpl extends PgStatAllIndexesRepo {
  override def selectAll(implicit c: Connection): List[PgStatAllIndexesRow] = {
    SQL"""select relid, indexrelid, schemaname, relname, indexrelname, idx_scan, idx_tup_read, idx_tup_fetch from pg_catalog.pg_stat_all_indexes""".as(PgStatAllIndexesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatAllIndexesFieldValue[_]])(implicit c: Connection): List[PgStatAllIndexesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatAllIndexesFieldValue.relid(value) => NamedParameter("relid", ParameterValue.from(value))
          case PgStatAllIndexesFieldValue.indexrelid(value) => NamedParameter("indexrelid", ParameterValue.from(value))
          case PgStatAllIndexesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgStatAllIndexesFieldValue.relname(value) => NamedParameter("relname", ParameterValue.from(value))
          case PgStatAllIndexesFieldValue.indexrelname(value) => NamedParameter("indexrelname", ParameterValue.from(value))
          case PgStatAllIndexesFieldValue.idxScan(value) => NamedParameter("idx_scan", ParameterValue.from(value))
          case PgStatAllIndexesFieldValue.idxTupRead(value) => NamedParameter("idx_tup_read", ParameterValue.from(value))
          case PgStatAllIndexesFieldValue.idxTupFetch(value) => NamedParameter("idx_tup_fetch", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_stat_all_indexes where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatAllIndexesRow.rowParser.*)
    }

  }
}
