package testdb
package postgres
package pg_catalog



sealed abstract class PgDefaultAclFieldValue[T](val name: String, val value: T)

object PgDefaultAclFieldValue {
  case class oid(override val value: Long) extends PgDefaultAclFieldValue("oid", value)
  case class defaclrole(override val value: Long) extends PgDefaultAclFieldValue("defaclrole", value)
  case class defaclnamespace(override val value: Long) extends PgDefaultAclFieldValue("defaclnamespace", value)
  case class defaclobjtype(override val value: String) extends PgDefaultAclFieldValue("defaclobjtype", value)
  case class defaclacl(override val value: Array[String]) extends PgDefaultAclFieldValue("defaclacl", value)
}
