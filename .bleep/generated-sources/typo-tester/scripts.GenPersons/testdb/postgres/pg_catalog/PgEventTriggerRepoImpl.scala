package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgEventTriggerRepoImpl extends PgEventTriggerRepo {
  override def selectAll(implicit c: Connection): List[PgEventTriggerRow] = {
    SQL"""select oid, evtname, evtevent, evtowner, evtfoid, evtenabled, evttags from pg_catalog.pg_event_trigger""".as(PgEventTriggerRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgEventTriggerFieldValue[_]])(implicit c: Connection): List[PgEventTriggerRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgEventTriggerFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgEventTriggerFieldValue.evtname(value) => NamedParameter("evtname", ParameterValue.from(value))
          case PgEventTriggerFieldValue.evtevent(value) => NamedParameter("evtevent", ParameterValue.from(value))
          case PgEventTriggerFieldValue.evtowner(value) => NamedParameter("evtowner", ParameterValue.from(value))
          case PgEventTriggerFieldValue.evtfoid(value) => NamedParameter("evtfoid", ParameterValue.from(value))
          case PgEventTriggerFieldValue.evtenabled(value) => NamedParameter("evtenabled", ParameterValue.from(value))
          case PgEventTriggerFieldValue.evttags(value) => NamedParameter("evttags", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_event_trigger where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgEventTriggerRow.rowParser.*)
    }

  }
}
