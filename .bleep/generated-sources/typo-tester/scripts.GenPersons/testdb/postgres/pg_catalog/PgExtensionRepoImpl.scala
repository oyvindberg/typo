package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgExtensionRepoImpl extends PgExtensionRepo {
  override def selectAll(implicit c: Connection): List[PgExtensionRow] = {
    SQL"""select oid, extname, extowner, extnamespace, extrelocatable, extversion, extconfig, extcondition from pg_catalog.pg_extension""".as(PgExtensionRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgExtensionFieldValue[_]])(implicit c: Connection): List[PgExtensionRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgExtensionFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgExtensionFieldValue.extname(value) => NamedParameter("extname", ParameterValue.from(value))
          case PgExtensionFieldValue.extowner(value) => NamedParameter("extowner", ParameterValue.from(value))
          case PgExtensionFieldValue.extnamespace(value) => NamedParameter("extnamespace", ParameterValue.from(value))
          case PgExtensionFieldValue.extrelocatable(value) => NamedParameter("extrelocatable", ParameterValue.from(value))
          case PgExtensionFieldValue.extversion(value) => NamedParameter("extversion", ParameterValue.from(value))
          case PgExtensionFieldValue.extconfig(value) => NamedParameter("extconfig", ParameterValue.from(value))
          case PgExtensionFieldValue.extcondition(value) => NamedParameter("extcondition", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_extension where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgExtensionRow.rowParser.*)
    }

  }
}
