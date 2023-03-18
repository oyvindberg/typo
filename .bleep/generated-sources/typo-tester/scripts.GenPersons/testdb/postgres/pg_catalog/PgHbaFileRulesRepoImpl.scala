package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgHbaFileRulesRepoImpl extends PgHbaFileRulesRepo {
  override def selectAll(implicit c: Connection): List[PgHbaFileRulesRow] = {
    SQL"""select line_number, type, database, user_name, address, netmask, auth_method, options, error from pg_catalog.pg_hba_file_rules""".as(PgHbaFileRulesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgHbaFileRulesFieldValue[_]])(implicit c: Connection): List[PgHbaFileRulesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgHbaFileRulesFieldValue.lineNumber(value) => NamedParameter("line_number", ParameterValue.from(value))
          case PgHbaFileRulesFieldValue.`type`(value) => NamedParameter("type", ParameterValue.from(value))
          case PgHbaFileRulesFieldValue.database(value) => NamedParameter("database", ParameterValue.from(value))
          case PgHbaFileRulesFieldValue.userName(value) => NamedParameter("user_name", ParameterValue.from(value))
          case PgHbaFileRulesFieldValue.address(value) => NamedParameter("address", ParameterValue.from(value))
          case PgHbaFileRulesFieldValue.netmask(value) => NamedParameter("netmask", ParameterValue.from(value))
          case PgHbaFileRulesFieldValue.authMethod(value) => NamedParameter("auth_method", ParameterValue.from(value))
          case PgHbaFileRulesFieldValue.options(value) => NamedParameter("options", ParameterValue.from(value))
          case PgHbaFileRulesFieldValue.error(value) => NamedParameter("error", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_hba_file_rules where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgHbaFileRulesRow.rowParser.*)
    }

  }
}
