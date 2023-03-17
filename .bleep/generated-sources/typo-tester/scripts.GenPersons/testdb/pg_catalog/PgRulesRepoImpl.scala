package testdb.pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgRulesRepoImpl extends PgRulesRepo {
  override def selectAll(implicit c: Connection): List[PgRulesRow] = {
    SQL"""select schemaname, tablename, rulename, definition from pg_catalog.pg_rules""".as(PgRulesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgRulesFieldValue[_]])(implicit c: Connection): List[PgRulesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgRulesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgRulesFieldValue.tablename(value) => NamedParameter("tablename", ParameterValue.from(value))
          case PgRulesFieldValue.rulename(value) => NamedParameter("rulename", ParameterValue.from(value))
          case PgRulesFieldValue.definition(value) => NamedParameter("definition", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_rules where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgRulesRow.rowParser.*)
    }

  }
}
