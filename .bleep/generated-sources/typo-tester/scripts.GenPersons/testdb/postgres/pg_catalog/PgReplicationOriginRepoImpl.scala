package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgReplicationOriginRepoImpl extends PgReplicationOriginRepo {
  override def selectAll(implicit c: Connection): List[PgReplicationOriginRow] = {
    SQL"""select roident, roname from pg_catalog.pg_replication_origin""".as(PgReplicationOriginRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgReplicationOriginFieldValue[_]])(implicit c: Connection): List[PgReplicationOriginRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgReplicationOriginFieldValue.roident(value) => NamedParameter("roident", ParameterValue.from(value))
          case PgReplicationOriginFieldValue.roname(value) => NamedParameter("roname", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_replication_origin where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgReplicationOriginRow.rowParser.*)
    }

  }
}
