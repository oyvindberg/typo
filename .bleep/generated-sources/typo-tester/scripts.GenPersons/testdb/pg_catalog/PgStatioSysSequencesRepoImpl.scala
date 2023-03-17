package testdb
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatioSysSequencesRepoImpl extends PgStatioSysSequencesRepo {
  override def selectAll(implicit c: Connection): List[PgStatioSysSequencesRow] = {
    SQL"""select relid, schemaname, relname, blks_read, blks_hit from pg_catalog.pg_statio_sys_sequences""".as(PgStatioSysSequencesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatioSysSequencesFieldValue[_]])(implicit c: Connection): List[PgStatioSysSequencesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatioSysSequencesFieldValue.relid(value) => NamedParameter("relid", ParameterValue.from(value))
          case PgStatioSysSequencesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgStatioSysSequencesFieldValue.relname(value) => NamedParameter("relname", ParameterValue.from(value))
          case PgStatioSysSequencesFieldValue.blksRead(value) => NamedParameter("blks_read", ParameterValue.from(value))
          case PgStatioSysSequencesFieldValue.blksHit(value) => NamedParameter("blks_hit", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_statio_sys_sequences where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatioSysSequencesRow.rowParser.*)
    }

  }
}
