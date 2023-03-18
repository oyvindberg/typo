package testdb
package postgres
package pg_catalog



sealed abstract class PgTypeFieldValue[T](val name: String, val value: T)

object PgTypeFieldValue {
  case class oid(override val value: Long) extends PgTypeFieldValue("oid", value)
  case class typname(override val value: String) extends PgTypeFieldValue("typname", value)
  case class typnamespace(override val value: Long) extends PgTypeFieldValue("typnamespace", value)
  case class typowner(override val value: Long) extends PgTypeFieldValue("typowner", value)
  case class typlen(override val value: Short) extends PgTypeFieldValue("typlen", value)
  case class typbyval(override val value: Boolean) extends PgTypeFieldValue("typbyval", value)
  case class typtype(override val value: String) extends PgTypeFieldValue("typtype", value)
  case class typcategory(override val value: String) extends PgTypeFieldValue("typcategory", value)
  case class typispreferred(override val value: Boolean) extends PgTypeFieldValue("typispreferred", value)
  case class typisdefined(override val value: Boolean) extends PgTypeFieldValue("typisdefined", value)
  case class typdelim(override val value: String) extends PgTypeFieldValue("typdelim", value)
  case class typrelid(override val value: Long) extends PgTypeFieldValue("typrelid", value)
  case class typsubscript(override val value: String) extends PgTypeFieldValue("typsubscript", value)
  case class typelem(override val value: Long) extends PgTypeFieldValue("typelem", value)
  case class typarray(override val value: Long) extends PgTypeFieldValue("typarray", value)
  case class typinput(override val value: String) extends PgTypeFieldValue("typinput", value)
  case class typoutput(override val value: String) extends PgTypeFieldValue("typoutput", value)
  case class typreceive(override val value: String) extends PgTypeFieldValue("typreceive", value)
  case class typsend(override val value: String) extends PgTypeFieldValue("typsend", value)
  case class typmodin(override val value: String) extends PgTypeFieldValue("typmodin", value)
  case class typmodout(override val value: String) extends PgTypeFieldValue("typmodout", value)
  case class typanalyze(override val value: String) extends PgTypeFieldValue("typanalyze", value)
  case class typalign(override val value: String) extends PgTypeFieldValue("typalign", value)
  case class typstorage(override val value: String) extends PgTypeFieldValue("typstorage", value)
  case class typnotnull(override val value: Boolean) extends PgTypeFieldValue("typnotnull", value)
  case class typbasetype(override val value: Long) extends PgTypeFieldValue("typbasetype", value)
  case class typtypmod(override val value: Int) extends PgTypeFieldValue("typtypmod", value)
  case class typndims(override val value: Int) extends PgTypeFieldValue("typndims", value)
  case class typcollation(override val value: Long) extends PgTypeFieldValue("typcollation", value)
  case class typdefaultbin(override val value: Option[String]) extends PgTypeFieldValue("typdefaultbin", value)
  case class typdefault(override val value: Option[String]) extends PgTypeFieldValue("typdefault", value)
  case class typacl(override val value: Option[Array[String]]) extends PgTypeFieldValue("typacl", value)
}
