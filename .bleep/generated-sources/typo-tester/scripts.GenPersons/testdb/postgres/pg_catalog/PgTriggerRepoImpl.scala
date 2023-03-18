package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTriggerRepoImpl extends PgTriggerRepo {
  override def selectAll(implicit c: Connection): List[PgTriggerRow] = {
    SQL"""select oid, tgrelid, tgparentid, tgname, tgfoid, tgtype, tgenabled, tgisinternal, tgconstrrelid, tgconstrindid, tgconstraint, tgdeferrable, tginitdeferred, tgnargs, tgattr, tgargs, tgqual, tgoldtable, tgnewtable from pg_catalog.pg_trigger""".as(PgTriggerRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTriggerFieldValue[_]])(implicit c: Connection): List[PgTriggerRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTriggerFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgTriggerFieldValue.tgrelid(value) => NamedParameter("tgrelid", ParameterValue.from(value))
          case PgTriggerFieldValue.tgparentid(value) => NamedParameter("tgparentid", ParameterValue.from(value))
          case PgTriggerFieldValue.tgname(value) => NamedParameter("tgname", ParameterValue.from(value))
          case PgTriggerFieldValue.tgfoid(value) => NamedParameter("tgfoid", ParameterValue.from(value))
          case PgTriggerFieldValue.tgtype(value) => NamedParameter("tgtype", ParameterValue.from(value))
          case PgTriggerFieldValue.tgenabled(value) => NamedParameter("tgenabled", ParameterValue.from(value))
          case PgTriggerFieldValue.tgisinternal(value) => NamedParameter("tgisinternal", ParameterValue.from(value))
          case PgTriggerFieldValue.tgconstrrelid(value) => NamedParameter("tgconstrrelid", ParameterValue.from(value))
          case PgTriggerFieldValue.tgconstrindid(value) => NamedParameter("tgconstrindid", ParameterValue.from(value))
          case PgTriggerFieldValue.tgconstraint(value) => NamedParameter("tgconstraint", ParameterValue.from(value))
          case PgTriggerFieldValue.tgdeferrable(value) => NamedParameter("tgdeferrable", ParameterValue.from(value))
          case PgTriggerFieldValue.tginitdeferred(value) => NamedParameter("tginitdeferred", ParameterValue.from(value))
          case PgTriggerFieldValue.tgnargs(value) => NamedParameter("tgnargs", ParameterValue.from(value))
          case PgTriggerFieldValue.tgattr(value) => NamedParameter("tgattr", ParameterValue.from(value))
          case PgTriggerFieldValue.tgargs(value) => NamedParameter("tgargs", ParameterValue.from(value))
          case PgTriggerFieldValue.tgqual(value) => NamedParameter("tgqual", ParameterValue.from(value))
          case PgTriggerFieldValue.tgoldtable(value) => NamedParameter("tgoldtable", ParameterValue.from(value))
          case PgTriggerFieldValue.tgnewtable(value) => NamedParameter("tgnewtable", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_trigger where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTriggerRow.rowParser.*)
    }

  }
}
