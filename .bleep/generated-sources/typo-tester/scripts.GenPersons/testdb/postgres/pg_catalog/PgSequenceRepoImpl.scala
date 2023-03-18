package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgSequenceRepoImpl extends PgSequenceRepo {
  override def selectAll(implicit c: Connection): List[PgSequenceRow] = {
    SQL"""select seqrelid, seqtypid, seqstart, seqincrement, seqmax, seqmin, seqcache, seqcycle from pg_catalog.pg_sequence""".as(PgSequenceRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgSequenceFieldValue[_]])(implicit c: Connection): List[PgSequenceRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgSequenceFieldValue.seqrelid(value) => NamedParameter("seqrelid", ParameterValue.from(value))
          case PgSequenceFieldValue.seqtypid(value) => NamedParameter("seqtypid", ParameterValue.from(value))
          case PgSequenceFieldValue.seqstart(value) => NamedParameter("seqstart", ParameterValue.from(value))
          case PgSequenceFieldValue.seqincrement(value) => NamedParameter("seqincrement", ParameterValue.from(value))
          case PgSequenceFieldValue.seqmax(value) => NamedParameter("seqmax", ParameterValue.from(value))
          case PgSequenceFieldValue.seqmin(value) => NamedParameter("seqmin", ParameterValue.from(value))
          case PgSequenceFieldValue.seqcache(value) => NamedParameter("seqcache", ParameterValue.from(value))
          case PgSequenceFieldValue.seqcycle(value) => NamedParameter("seqcycle", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_sequence where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgSequenceRow.rowParser.*)
    }

  }
}
