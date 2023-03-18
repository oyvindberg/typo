package testdb
package postgres
package information_schema



sealed abstract class SqlSizingFieldValue[T](val name: String, val value: T)

object SqlSizingFieldValue {
  case class sizingId(override val value: Option[Int]) extends SqlSizingFieldValue("sizing_id", value)
  case class sizingName(override val value: Option[String]) extends SqlSizingFieldValue("sizing_name", value)
  case class supportedValue(override val value: Option[Int]) extends SqlSizingFieldValue("supported_value", value)
  case class comments(override val value: Option[String]) extends SqlSizingFieldValue("comments", value)
}
