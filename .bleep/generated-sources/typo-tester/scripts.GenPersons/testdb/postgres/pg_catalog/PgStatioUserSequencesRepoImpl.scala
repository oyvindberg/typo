package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgStatioUserSequencesRepoImpl extends PgStatioUserSequencesRepo {
  override def selectAll(implicit c: Connection): List[PgStatioUserSequencesRow] = {
    SQL"""select relid, schemaname, relname, blks_read, blks_hit from pg_catalog.pg_statio_user_sequences""".as(PgStatioUserSequencesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgStatioUserSequencesFieldValue[_]])(implicit c: Connection): List[PgStatioUserSequencesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgStatioUserSequencesFieldValue.relid(value) => NamedParameter("relid", ParameterValue.from(value))
          case PgStatioUserSequencesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgStatioUserSequencesFieldValue.relname(value) => NamedParameter("relname", ParameterValue.from(value))
          case PgStatioUserSequencesFieldValue.blksRead(value) => NamedParameter("blks_read", ParameterValue.from(value))
          case PgStatioUserSequencesFieldValue.blksHit(value) => NamedParameter("blks_hit", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_statio_user_sequences where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgStatioUserSequencesRow.rowParser.*)
    }

  }
}
