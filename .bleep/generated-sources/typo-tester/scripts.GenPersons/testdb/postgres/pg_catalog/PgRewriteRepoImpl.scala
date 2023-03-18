package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgRewriteRepoImpl extends PgRewriteRepo {
  override def selectAll(implicit c: Connection): List[PgRewriteRow] = {
    SQL"""select oid, rulename, ev_class, ev_type, ev_enabled, is_instead, ev_qual, ev_action from pg_catalog.pg_rewrite""".as(PgRewriteRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgRewriteFieldValue[_]])(implicit c: Connection): List[PgRewriteRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgRewriteFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgRewriteFieldValue.rulename(value) => NamedParameter("rulename", ParameterValue.from(value))
          case PgRewriteFieldValue.evClass(value) => NamedParameter("ev_class", ParameterValue.from(value))
          case PgRewriteFieldValue.evType(value) => NamedParameter("ev_type", ParameterValue.from(value))
          case PgRewriteFieldValue.evEnabled(value) => NamedParameter("ev_enabled", ParameterValue.from(value))
          case PgRewriteFieldValue.isInstead(value) => NamedParameter("is_instead", ParameterValue.from(value))
          case PgRewriteFieldValue.evQual(value) => NamedParameter("ev_qual", ParameterValue.from(value))
          case PgRewriteFieldValue.evAction(value) => NamedParameter("ev_action", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_rewrite where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgRewriteRow.rowParser.*)
    }

  }
}
