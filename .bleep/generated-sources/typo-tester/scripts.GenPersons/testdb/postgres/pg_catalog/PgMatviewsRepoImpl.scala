package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgMatviewsRepoImpl extends PgMatviewsRepo {
  override def selectAll(implicit c: Connection): List[PgMatviewsRow] = {
    SQL"""select schemaname, matviewname, matviewowner, tablespace, hasindexes, ispopulated, definition from pg_catalog.pg_matviews""".as(PgMatviewsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgMatviewsFieldValue[_]])(implicit c: Connection): List[PgMatviewsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgMatviewsFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgMatviewsFieldValue.matviewname(value) => NamedParameter("matviewname", ParameterValue.from(value))
          case PgMatviewsFieldValue.matviewowner(value) => NamedParameter("matviewowner", ParameterValue.from(value))
          case PgMatviewsFieldValue.tablespace(value) => NamedParameter("tablespace", ParameterValue.from(value))
          case PgMatviewsFieldValue.hasindexes(value) => NamedParameter("hasindexes", ParameterValue.from(value))
          case PgMatviewsFieldValue.ispopulated(value) => NamedParameter("ispopulated", ParameterValue.from(value))
          case PgMatviewsFieldValue.definition(value) => NamedParameter("definition", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_matviews where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgMatviewsRow.rowParser.*)
    }

  }
}
