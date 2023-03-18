package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgPublicationRepoImpl extends PgPublicationRepo {
  override def selectAll(implicit c: Connection): List[PgPublicationRow] = {
    SQL"""select oid, pubname, pubowner, puballtables, pubinsert, pubupdate, pubdelete, pubtruncate, pubviaroot from pg_catalog.pg_publication""".as(PgPublicationRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgPublicationFieldValue[_]])(implicit c: Connection): List[PgPublicationRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgPublicationFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgPublicationFieldValue.pubname(value) => NamedParameter("pubname", ParameterValue.from(value))
          case PgPublicationFieldValue.pubowner(value) => NamedParameter("pubowner", ParameterValue.from(value))
          case PgPublicationFieldValue.puballtables(value) => NamedParameter("puballtables", ParameterValue.from(value))
          case PgPublicationFieldValue.pubinsert(value) => NamedParameter("pubinsert", ParameterValue.from(value))
          case PgPublicationFieldValue.pubupdate(value) => NamedParameter("pubupdate", ParameterValue.from(value))
          case PgPublicationFieldValue.pubdelete(value) => NamedParameter("pubdelete", ParameterValue.from(value))
          case PgPublicationFieldValue.pubtruncate(value) => NamedParameter("pubtruncate", ParameterValue.from(value))
          case PgPublicationFieldValue.pubviaroot(value) => NamedParameter("pubviaroot", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_publication where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgPublicationRow.rowParser.*)
    }

  }
}
