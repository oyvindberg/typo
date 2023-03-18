package testdb
package postgres
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait SqlSizingRepoImpl extends SqlSizingRepo {
  override def selectAll(implicit c: Connection): List[SqlSizingRow] = {
    SQL"""select sizing_id, sizing_name, supported_value, comments from information_schema.sql_sizing""".as(SqlSizingRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[SqlSizingFieldValue[_]])(implicit c: Connection): List[SqlSizingRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case SqlSizingFieldValue.sizingId(value) => NamedParameter("sizing_id", ParameterValue.from(value))
          case SqlSizingFieldValue.sizingName(value) => NamedParameter("sizing_name", ParameterValue.from(value))
          case SqlSizingFieldValue.supportedValue(value) => NamedParameter("supported_value", ParameterValue.from(value))
          case SqlSizingFieldValue.comments(value) => NamedParameter("comments", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.sql_sizing where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(SqlSizingRow.rowParser.*)
    }

  }
}
