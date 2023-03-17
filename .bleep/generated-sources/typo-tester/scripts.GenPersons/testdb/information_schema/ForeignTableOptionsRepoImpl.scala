package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ForeignTableOptionsRepoImpl extends ForeignTableOptionsRepo {
  override def selectAll(implicit c: Connection): List[ForeignTableOptionsRow] = {
    SQL"""select foreign_table_catalog, foreign_table_schema, foreign_table_name, option_name, option_value from information_schema.foreign_table_options""".as(ForeignTableOptionsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ForeignTableOptionsFieldValue[_]])(implicit c: Connection): List[ForeignTableOptionsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ForeignTableOptionsFieldValue.foreignTableCatalog(value) => NamedParameter("foreign_table_catalog", ParameterValue.from(value))
          case ForeignTableOptionsFieldValue.foreignTableSchema(value) => NamedParameter("foreign_table_schema", ParameterValue.from(value))
          case ForeignTableOptionsFieldValue.foreignTableName(value) => NamedParameter("foreign_table_name", ParameterValue.from(value))
          case ForeignTableOptionsFieldValue.optionName(value) => NamedParameter("option_name", ParameterValue.from(value))
          case ForeignTableOptionsFieldValue.optionValue(value) => NamedParameter("option_value", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.foreign_table_options where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ForeignTableOptionsRow.rowParser.*)
    }

  }
}
