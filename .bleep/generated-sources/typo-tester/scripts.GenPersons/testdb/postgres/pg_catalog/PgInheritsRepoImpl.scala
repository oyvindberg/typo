package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgInheritsRepoImpl extends PgInheritsRepo {
  override def selectAll(implicit c: Connection): List[PgInheritsRow] = {
    SQL"""select inhrelid, inhparent, inhseqno, inhdetachpending from pg_catalog.pg_inherits""".as(PgInheritsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgInheritsFieldValue[_]])(implicit c: Connection): List[PgInheritsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgInheritsFieldValue.inhrelid(value) => NamedParameter("inhrelid", ParameterValue.from(value))
          case PgInheritsFieldValue.inhparent(value) => NamedParameter("inhparent", ParameterValue.from(value))
          case PgInheritsFieldValue.inhseqno(value) => NamedParameter("inhseqno", ParameterValue.from(value))
          case PgInheritsFieldValue.inhdetachpending(value) => NamedParameter("inhdetachpending", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_inherits where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgInheritsRow.rowParser.*)
    }

  }
}
