package testdb
package postgres
package information_schema



sealed abstract class SqlFeaturesFieldValue[T](val name: String, val value: T)

object SqlFeaturesFieldValue {
  case class featureId(override val value: Option[String]) extends SqlFeaturesFieldValue("feature_id", value)
  case class featureName(override val value: Option[String]) extends SqlFeaturesFieldValue("feature_name", value)
  case class subFeatureId(override val value: Option[String]) extends SqlFeaturesFieldValue("sub_feature_id", value)
  case class subFeatureName(override val value: Option[String]) extends SqlFeaturesFieldValue("sub_feature_name", value)
  case class isSupported(override val value: Option[String]) extends SqlFeaturesFieldValue("is_supported", value)
  case class isVerifiedBy(override val value: Option[String]) extends SqlFeaturesFieldValue("is_verified_by", value)
  case class comments(override val value: Option[String]) extends SqlFeaturesFieldValue("comments", value)
}
