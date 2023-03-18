package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgPublicationTablesRepoImpl extends PgPublicationTablesRepo {
  override def selectAll(implicit c: Connection): List[PgPublicationTablesRow] = {
    SQL"""select pubname, schemaname, tablename from pg_catalog.pg_publication_tables""".as(PgPublicationTablesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgPublicationTablesFieldValue[_]])(implicit c: Connection): List[PgPublicationTablesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgPublicationTablesFieldValue.pubname(value) => NamedParameter("pubname", ParameterValue.from(value))
          case PgPublicationTablesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgPublicationTablesFieldValue.tablename(value) => NamedParameter("tablename", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_publication_tables where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgPublicationTablesRow.rowParser.*)
    }

  }
}
