package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgSubscriptionRelRepoImpl extends PgSubscriptionRelRepo {
  override def selectAll(implicit c: Connection): List[PgSubscriptionRelRow] = {
    SQL"""select srsubid, srrelid, srsubstate, srsublsn from pg_catalog.pg_subscription_rel""".as(PgSubscriptionRelRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgSubscriptionRelFieldValue[_]])(implicit c: Connection): List[PgSubscriptionRelRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgSubscriptionRelFieldValue.srsubid(value) => NamedParameter("srsubid", ParameterValue.from(value))
          case PgSubscriptionRelFieldValue.srrelid(value) => NamedParameter("srrelid", ParameterValue.from(value))
          case PgSubscriptionRelFieldValue.srsubstate(value) => NamedParameter("srsubstate", ParameterValue.from(value))
          case PgSubscriptionRelFieldValue.srsublsn(value) => NamedParameter("srsublsn", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_subscription_rel where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgSubscriptionRelRow.rowParser.*)
    }

  }
}
