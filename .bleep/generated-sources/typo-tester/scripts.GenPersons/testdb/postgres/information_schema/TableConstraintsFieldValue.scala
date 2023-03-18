package testdb
package postgres
package information_schema



sealed abstract class TableConstraintsFieldValue[T](val name: String, val value: T)

object TableConstraintsFieldValue {
  case class constraintCatalog(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("constraint_schema", value)
  case class constraintName(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("constraint_name", value)
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("table_name", value)
  case class constraintType(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("constraint_type", value)
  case class isDeferrable(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("is_deferrable", value)
  case class initiallyDeferred(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("initially_deferred", value)
  case class enforced(override val value: /* unknown nullability */ Option[String]) extends TableConstraintsFieldValue("enforced", value)
}
