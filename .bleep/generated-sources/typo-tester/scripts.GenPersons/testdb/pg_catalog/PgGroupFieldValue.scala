package testdb
package pg_catalog



sealed abstract class PgGroupFieldValue[T](val name: String, val value: T)

object PgGroupFieldValue {
  case class groname(override val value: String) extends PgGroupFieldValue("groname", value)
  case class grosysid(override val value: Long) extends PgGroupFieldValue("grosysid", value)
  case class grolist(override val value: /* typo doesn't know how to translate: columnType: Array, columnTypeName: _oid, columnClassName: java.sql.Array */ Any) extends PgGroupFieldValue("grolist", value)
}
