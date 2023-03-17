package testdb
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatioSysIndexesRepoImpl extends PgStatioSysIndexesRepo {
  override def selectAll(implicit c: Connection): List[PgStatioSysIndexesRow] = {
    SQL"""select relid, indexrelid, schemaname, relname, indexrelname, idx_blks_read, idx_blks_hit from pg_catalog.pg_statio_sys_indexes""".as(PgStatioSysIndexesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatioSysIndexesFieldValue[_]])(implicit c: Connection): List[PgStatioSysIndexesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatioSysIndexesFieldValue.relid(value) => NamedParameter("relid", ParameterValue.from(value))
          case PgStatioSysIndexesFieldValue.indexrelid(value) => NamedParameter("indexrelid", ParameterValue.from(value))
          case PgStatioSysIndexesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgStatioSysIndexesFieldValue.relname(value) => NamedParameter("relname", ParameterValue.from(value))
          case PgStatioSysIndexesFieldValue.indexrelname(value) => NamedParameter("indexrelname", ParameterValue.from(value))
          case PgStatioSysIndexesFieldValue.idxBlksRead(value) => NamedParameter("idx_blks_read", ParameterValue.from(value))
          case PgStatioSysIndexesFieldValue.idxBlksHit(value) => NamedParameter("idx_blks_hit", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_statio_sys_indexes where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatioSysIndexesRow.rowParser.*)
    }

  }
}
