package testdb
package postgres
package public



sealed abstract class FooFieldValue[T](val name: String, val value: T)

object FooFieldValue {
  case class constraintCatalog(override val value: Option[String]) extends FooFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: Option[String]) extends FooFieldValue("constraint_schema", value)
  case class constraintName(override val value: Option[String]) extends FooFieldValue("constraint_name", value)
  case class tableCatalog(override val value: Option[String]) extends FooFieldValue("table_catalog", value)
  case class tableSchema(override val value: Option[String]) extends FooFieldValue("table_schema", value)
  case class tableName(override val value: Option[String]) extends FooFieldValue("table_name", value)
  case class constraintType(override val value: Option[String]) extends FooFieldValue("constraint_type", value)
  case class isDeferrable(override val value: Option[String]) extends FooFieldValue("is_deferrable", value)
  case class initiallyDeferred(override val value: Option[String]) extends FooFieldValue("initially_deferred", value)
  case class enforced(override val value: Option[String]) extends FooFieldValue("enforced", value)
}
