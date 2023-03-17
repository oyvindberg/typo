package testdb.information_schema



sealed abstract class PgForeignDataWrappersFieldValue[T](val name: String, val value: T)

object PgForeignDataWrappersFieldValue {
  case class oid(override val value: Long) extends PgForeignDataWrappersFieldValue("oid", value)
  case class fdwowner(override val value: Long) extends PgForeignDataWrappersFieldValue("fdwowner", value)
  case class fdwoptions(override val value: Option[Array[String]]) extends PgForeignDataWrappersFieldValue("fdwoptions", value)
  case class foreignDataWrapperCatalog(override val value: /* unknown nullability */ Option[String]) extends PgForeignDataWrappersFieldValue("foreign_data_wrapper_catalog", value)
  case class foreignDataWrapperName(override val value: /* unknown nullability */ Option[String]) extends PgForeignDataWrappersFieldValue("foreign_data_wrapper_name", value)
  case class authorizationIdentifier(override val value: /* unknown nullability */ Option[String]) extends PgForeignDataWrappersFieldValue("authorization_identifier", value)
  case class foreignDataWrapperLanguage(override val value: /* unknown nullability */ Option[String]) extends PgForeignDataWrappersFieldValue("foreign_data_wrapper_language", value)
}
