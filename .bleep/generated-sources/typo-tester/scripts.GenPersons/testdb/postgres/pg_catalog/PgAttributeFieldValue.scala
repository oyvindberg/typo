package testdb
package postgres
package pg_catalog



sealed abstract class PgAttributeFieldValue[T](val name: String, val value: T)

object PgAttributeFieldValue {
  case class attrelid(override val value: Long) extends PgAttributeFieldValue("attrelid", value)
  case class attname(override val value: String) extends PgAttributeFieldValue("attname", value)
  case class atttypid(override val value: Long) extends PgAttributeFieldValue("atttypid", value)
  case class attstattarget(override val value: Int) extends PgAttributeFieldValue("attstattarget", value)
  case class attlen(override val value: Short) extends PgAttributeFieldValue("attlen", value)
  case class attnum(override val value: Short) extends PgAttributeFieldValue("attnum", value)
  case class attndims(override val value: Int) extends PgAttributeFieldValue("attndims", value)
  case class attcacheoff(override val value: Int) extends PgAttributeFieldValue("attcacheoff", value)
  case class atttypmod(override val value: Int) extends PgAttributeFieldValue("atttypmod", value)
  case class attbyval(override val value: Boolean) extends PgAttributeFieldValue("attbyval", value)
  case class attalign(override val value: String) extends PgAttributeFieldValue("attalign", value)
  case class attstorage(override val value: String) extends PgAttributeFieldValue("attstorage", value)
  case class attcompression(override val value: String) extends PgAttributeFieldValue("attcompression", value)
  case class attnotnull(override val value: Boolean) extends PgAttributeFieldValue("attnotnull", value)
  case class atthasdef(override val value: Boolean) extends PgAttributeFieldValue("atthasdef", value)
  case class atthasmissing(override val value: Boolean) extends PgAttributeFieldValue("atthasmissing", value)
  case class attidentity(override val value: String) extends PgAttributeFieldValue("attidentity", value)
  case class attgenerated(override val value: String) extends PgAttributeFieldValue("attgenerated", value)
  case class attisdropped(override val value: Boolean) extends PgAttributeFieldValue("attisdropped", value)
  case class attislocal(override val value: Boolean) extends PgAttributeFieldValue("attislocal", value)
  case class attinhcount(override val value: Int) extends PgAttributeFieldValue("attinhcount", value)
  case class attcollation(override val value: Long) extends PgAttributeFieldValue("attcollation", value)
  case class attacl(override val value: Option[Array[String]]) extends PgAttributeFieldValue("attacl", value)
  case class attoptions(override val value: Option[Array[String]]) extends PgAttributeFieldValue("attoptions", value)
  case class attfdwoptions(override val value: Option[Array[String]]) extends PgAttributeFieldValue("attfdwoptions", value)
  case class attmissingval(override val value: Option[String]) extends PgAttributeFieldValue("attmissingval", value)
}
