package testdb
package postgres
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait SqlPartsRepoImpl extends SqlPartsRepo {
  override def selectAll(implicit c: Connection): List[SqlPartsRow] = {
    SQL"""select feature_id, feature_name, is_supported, is_verified_by, comments from information_schema.sql_parts""".as(SqlPartsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[SqlPartsFieldValue[_]])(implicit c: Connection): List[SqlPartsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case SqlPartsFieldValue.featureId(value) => NamedParameter("feature_id", ParameterValue.from(value))
          case SqlPartsFieldValue.featureName(value) => NamedParameter("feature_name", ParameterValue.from(value))
          case SqlPartsFieldValue.isSupported(value) => NamedParameter("is_supported", ParameterValue.from(value))
          case SqlPartsFieldValue.isVerifiedBy(value) => NamedParameter("is_verified_by", ParameterValue.from(value))
          case SqlPartsFieldValue.comments(value) => NamedParameter("comments", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.sql_parts where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(SqlPartsRow.rowParser.*)
    }

  }
}
