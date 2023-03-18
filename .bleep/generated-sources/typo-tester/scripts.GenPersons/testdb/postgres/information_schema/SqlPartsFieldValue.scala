package testdb
package postgres
package information_schema



sealed abstract class SqlPartsFieldValue[T](val name: String, val value: T)

object SqlPartsFieldValue {
  case class featureId(override val value: Option[String]) extends SqlPartsFieldValue("feature_id", value)
  case class featureName(override val value: Option[String]) extends SqlPartsFieldValue("feature_name", value)
  case class isSupported(override val value: Option[String]) extends SqlPartsFieldValue("is_supported", value)
  case class isVerifiedBy(override val value: Option[String]) extends SqlPartsFieldValue("is_verified_by", value)
  case class comments(override val value: Option[String]) extends SqlPartsFieldValue("comments", value)
}
