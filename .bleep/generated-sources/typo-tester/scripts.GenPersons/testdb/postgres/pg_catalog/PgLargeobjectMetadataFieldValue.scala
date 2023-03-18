package testdb
package postgres
package pg_catalog



sealed abstract class PgLargeobjectMetadataFieldValue[T](val name: String, val value: T)

object PgLargeobjectMetadataFieldValue {
  case class oid(override val value: PgLargeobjectMetadataId) extends PgLargeobjectMetadataFieldValue("oid", value)
  case class lomowner(override val value: Long) extends PgLargeobjectMetadataFieldValue("lomowner", value)
  case class lomacl(override val value: Option[Array[String]]) extends PgLargeobjectMetadataFieldValue("lomacl", value)
}
