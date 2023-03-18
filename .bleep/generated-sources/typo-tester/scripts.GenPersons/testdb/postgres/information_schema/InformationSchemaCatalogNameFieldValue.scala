package testdb
package postgres
package information_schema



sealed abstract class InformationSchemaCatalogNameFieldValue[T](val name: String, val value: T)

object InformationSchemaCatalogNameFieldValue {
  case class catalogName(override val value: /* unknown nullability */ Option[String]) extends InformationSchemaCatalogNameFieldValue("catalog_name", value)
}
