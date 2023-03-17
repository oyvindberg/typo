package testdb.information_schema



sealed abstract class ForeignDataWrappersFieldValue[T](val name: String, val value: T)

object ForeignDataWrappersFieldValue {
  case class foreignDataWrapperCatalog(override val value: Option[String]) extends ForeignDataWrappersFieldValue("foreign_data_wrapper_catalog", value)
  case class foreignDataWrapperName(override val value: Option[String]) extends ForeignDataWrappersFieldValue("foreign_data_wrapper_name", value)
  case class authorizationIdentifier(override val value: Option[String]) extends ForeignDataWrappersFieldValue("authorization_identifier", value)
  case class libraryName(override val value: /* unknown nullability */ Option[String]) extends ForeignDataWrappersFieldValue("library_name", value)
  case class foreignDataWrapperLanguage(override val value: Option[String]) extends ForeignDataWrappersFieldValue("foreign_data_wrapper_language", value)
}
