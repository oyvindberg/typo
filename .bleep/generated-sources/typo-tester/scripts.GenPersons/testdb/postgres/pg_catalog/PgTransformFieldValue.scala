package testdb
package postgres
package pg_catalog



sealed abstract class PgTransformFieldValue[T](val name: String, val value: T)

object PgTransformFieldValue {
  case class oid(override val value: Long) extends PgTransformFieldValue("oid", value)
  case class trftype(override val value: Long) extends PgTransformFieldValue("trftype", value)
  case class trflang(override val value: Long) extends PgTransformFieldValue("trflang", value)
  case class trffromsql(override val value: String) extends PgTransformFieldValue("trffromsql", value)
  case class trftosql(override val value: String) extends PgTransformFieldValue("trftosql", value)
}
