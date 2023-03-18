package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgPublicationRelRepoImpl extends PgPublicationRelRepo {
  override def selectAll(implicit c: Connection): List[PgPublicationRelRow] = {
    SQL"""select oid, prpubid, prrelid from pg_catalog.pg_publication_rel""".as(PgPublicationRelRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgPublicationRelFieldValue[_]])(implicit c: Connection): List[PgPublicationRelRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgPublicationRelFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgPublicationRelFieldValue.prpubid(value) => NamedParameter("prpubid", ParameterValue.from(value))
          case PgPublicationRelFieldValue.prrelid(value) => NamedParameter("prrelid", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_publication_rel where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgPublicationRelRow.rowParser.*)
    }

  }
}
