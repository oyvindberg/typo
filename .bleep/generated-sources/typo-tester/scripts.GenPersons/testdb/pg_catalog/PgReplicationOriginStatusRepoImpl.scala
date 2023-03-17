package testdb.pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgReplicationOriginStatusRepoImpl extends PgReplicationOriginStatusRepo {
  override def selectAll(implicit c: Connection): List[PgReplicationOriginStatusRow] = {
    SQL"""select local_id, external_id, remote_lsn, local_lsn from pg_catalog.pg_replication_origin_status""".as(PgReplicationOriginStatusRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgReplicationOriginStatusFieldValue[_]])(implicit c: Connection): List[PgReplicationOriginStatusRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgReplicationOriginStatusFieldValue.localId(value) => NamedParameter("local_id", ParameterValue.from(value))
          case PgReplicationOriginStatusFieldValue.externalId(value) => NamedParameter("external_id", ParameterValue.from(value))
          case PgReplicationOriginStatusFieldValue.remoteLsn(value) => NamedParameter("remote_lsn", ParameterValue.from(value))
          case PgReplicationOriginStatusFieldValue.localLsn(value) => NamedParameter("local_lsn", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_replication_origin_status where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgReplicationOriginStatusRow.rowParser.*)
    }

  }
}
