package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgSubscriptionRepoImpl extends PgSubscriptionRepo {
  override def selectAll(implicit c: Connection): List[PgSubscriptionRow] = {
    SQL"""select oid, subdbid, subname, subowner, subenabled, subbinary, substream, subconninfo, subslotname, subsynccommit, subpublications from pg_catalog.pg_subscription""".as(PgSubscriptionRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgSubscriptionFieldValue[_]])(implicit c: Connection): List[PgSubscriptionRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgSubscriptionFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subdbid(value) => NamedParameter("subdbid", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subname(value) => NamedParameter("subname", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subowner(value) => NamedParameter("subowner", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subenabled(value) => NamedParameter("subenabled", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subbinary(value) => NamedParameter("subbinary", ParameterValue.from(value))
          case PgSubscriptionFieldValue.substream(value) => NamedParameter("substream", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subconninfo(value) => NamedParameter("subconninfo", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subslotname(value) => NamedParameter("subslotname", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subsynccommit(value) => NamedParameter("subsynccommit", ParameterValue.from(value))
          case PgSubscriptionFieldValue.subpublications(value) => NamedParameter("subpublications", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_subscription where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgSubscriptionRow.rowParser.*)
    }

  }
}
