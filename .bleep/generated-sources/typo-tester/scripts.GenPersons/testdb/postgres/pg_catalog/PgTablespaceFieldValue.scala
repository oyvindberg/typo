package testdb
package postgres
package pg_catalog



sealed abstract class PgTablespaceFieldValue[T](val name: String, val value: T)

object PgTablespaceFieldValue {
  case class oid(override val value: Long) extends PgTablespaceFieldValue("oid", value)
  case class spcname(override val value: String) extends PgTablespaceFieldValue("spcname", value)
  case class spcowner(override val value: Long) extends PgTablespaceFieldValue("spcowner", value)
  case class spcacl(override val value: Option[Array[String]]) extends PgTablespaceFieldValue("spcacl", value)
  case class spcoptions(override val value: Option[Array[String]]) extends PgTablespaceFieldValue("spcoptions", value)
}
