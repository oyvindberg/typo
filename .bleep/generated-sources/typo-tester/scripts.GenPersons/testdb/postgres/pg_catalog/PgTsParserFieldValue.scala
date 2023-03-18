package testdb
package postgres
package pg_catalog



sealed abstract class PgTsParserFieldValue[T](val name: String, val value: T)

object PgTsParserFieldValue {
  case class oid(override val value: PgTsParserId) extends PgTsParserFieldValue("oid", value)
  case class prsname(override val value: String) extends PgTsParserFieldValue("prsname", value)
  case class prsnamespace(override val value: Long) extends PgTsParserFieldValue("prsnamespace", value)
  case class prsstart(override val value: String) extends PgTsParserFieldValue("prsstart", value)
  case class prstoken(override val value: String) extends PgTsParserFieldValue("prstoken", value)
  case class prsend(override val value: String) extends PgTsParserFieldValue("prsend", value)
  case class prsheadline(override val value: String) extends PgTsParserFieldValue("prsheadline", value)
  case class prslextype(override val value: String) extends PgTsParserFieldValue("prslextype", value)
}
