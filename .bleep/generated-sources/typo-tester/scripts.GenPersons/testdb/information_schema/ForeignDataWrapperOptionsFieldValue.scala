package testdb
package information_schema



sealed abstract class ForeignDataWrapperOptionsFieldValue[T](val name: String, val value: T)

object ForeignDataWrapperOptionsFieldValue {
  case class foreignDataWrapperCatalog(override val value: Option[String]) extends ForeignDataWrapperOptionsFieldValue("foreign_data_wrapper_catalog", value)
  case class foreignDataWrapperName(override val value: Option[String]) extends ForeignDataWrapperOptionsFieldValue("foreign_data_wrapper_name", value)
  case class optionName(override val value: /* unknown nullability */ Option[String]) extends ForeignDataWrapperOptionsFieldValue("option_name", value)
  case class optionValue(override val value: /* unknown nullability */ Option[String]) extends ForeignDataWrapperOptionsFieldValue("option_value", value)
}
