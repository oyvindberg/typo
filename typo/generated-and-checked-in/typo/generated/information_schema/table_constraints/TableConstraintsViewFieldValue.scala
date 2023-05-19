/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources`)
 */
package typo
package generated
package information_schema
package table_constraints

import typo.generated.information_schema.CharacterData
import typo.generated.information_schema.SqlIdentifier
import typo.generated.information_schema.YesOrNo

sealed abstract class TableConstraintsViewFieldOrIdValue[T](val name: String, val value: T)
sealed abstract class TableConstraintsViewFieldValue[T](name: String, value: T) extends TableConstraintsViewFieldOrIdValue(name, value)

object TableConstraintsViewFieldValue {
  case class constraintCatalog(override val value: Option[SqlIdentifier]) extends TableConstraintsViewFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: Option[SqlIdentifier]) extends TableConstraintsViewFieldValue("constraint_schema", value)
  case class constraintName(override val value: Option[SqlIdentifier]) extends TableConstraintsViewFieldValue("constraint_name", value)
  case class tableCatalog(override val value: Option[SqlIdentifier]) extends TableConstraintsViewFieldValue("table_catalog", value)
  case class tableSchema(override val value: Option[SqlIdentifier]) extends TableConstraintsViewFieldValue("table_schema", value)
  case class tableName(override val value: Option[SqlIdentifier]) extends TableConstraintsViewFieldValue("table_name", value)
  case class constraintType(override val value: Option[CharacterData]) extends TableConstraintsViewFieldValue("constraint_type", value)
  case class isDeferrable(override val value: Option[YesOrNo]) extends TableConstraintsViewFieldValue("is_deferrable", value)
  case class initiallyDeferred(override val value: Option[YesOrNo]) extends TableConstraintsViewFieldValue("initially_deferred", value)
  case class enforced(override val value: Option[YesOrNo]) extends TableConstraintsViewFieldValue("enforced", value)
  case class nullsDistinct(override val value: Option[YesOrNo]) extends TableConstraintsViewFieldValue("nulls_distinct", value)
}