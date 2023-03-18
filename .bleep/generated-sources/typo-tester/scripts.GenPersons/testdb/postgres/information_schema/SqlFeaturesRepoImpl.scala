package testdb
package postgres
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait SqlFeaturesRepoImpl extends SqlFeaturesRepo {
  override def selectAll(implicit c: Connection): List[SqlFeaturesRow] = {
    SQL"""select feature_id, feature_name, sub_feature_id, sub_feature_name, is_supported, is_verified_by, comments from information_schema.sql_features""".as(SqlFeaturesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[SqlFeaturesFieldValue[_]])(implicit c: Connection): List[SqlFeaturesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case SqlFeaturesFieldValue.featureId(value) => NamedParameter("feature_id", ParameterValue.from(value))
          case SqlFeaturesFieldValue.featureName(value) => NamedParameter("feature_name", ParameterValue.from(value))
          case SqlFeaturesFieldValue.subFeatureId(value) => NamedParameter("sub_feature_id", ParameterValue.from(value))
          case SqlFeaturesFieldValue.subFeatureName(value) => NamedParameter("sub_feature_name", ParameterValue.from(value))
          case SqlFeaturesFieldValue.isSupported(value) => NamedParameter("is_supported", ParameterValue.from(value))
          case SqlFeaturesFieldValue.isVerifiedBy(value) => NamedParameter("is_verified_by", ParameterValue.from(value))
          case SqlFeaturesFieldValue.comments(value) => NamedParameter("comments", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.sql_features where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(SqlFeaturesRow.rowParser.*)
    }

  }
}
