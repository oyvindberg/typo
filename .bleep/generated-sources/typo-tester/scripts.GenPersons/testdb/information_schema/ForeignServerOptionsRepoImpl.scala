package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ForeignServerOptionsRepoImpl extends ForeignServerOptionsRepo {
  override def selectAll(implicit c: Connection): List[ForeignServerOptionsRow] = {
    SQL"""select foreign_server_catalog, foreign_server_name, option_name, option_value from information_schema.foreign_server_options""".as(ForeignServerOptionsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ForeignServerOptionsFieldValue[_]])(implicit c: Connection): List[ForeignServerOptionsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ForeignServerOptionsFieldValue.foreignServerCatalog(value) => NamedParameter("foreign_server_catalog", ParameterValue.from(value))
          case ForeignServerOptionsFieldValue.foreignServerName(value) => NamedParameter("foreign_server_name", ParameterValue.from(value))
          case ForeignServerOptionsFieldValue.optionName(value) => NamedParameter("option_name", ParameterValue.from(value))
          case ForeignServerOptionsFieldValue.optionValue(value) => NamedParameter("option_value", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.foreign_server_options where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ForeignServerOptionsRow.rowParser.*)
    }

  }
}
