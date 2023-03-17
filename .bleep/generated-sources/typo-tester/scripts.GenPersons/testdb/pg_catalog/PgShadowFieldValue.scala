package testdb.pg_catalog

import java.time.LocalDateTime

sealed abstract class PgShadowFieldValue[T](val name: String, val value: T)

object PgShadowFieldValue {
  case class usename(override val value: String) extends PgShadowFieldValue("usename", value)
  case class usesysid(override val value: Long) extends PgShadowFieldValue("usesysid", value)
  case class usecreatedb(override val value: Boolean) extends PgShadowFieldValue("usecreatedb", value)
  case class usesuper(override val value: Boolean) extends PgShadowFieldValue("usesuper", value)
  case class userepl(override val value: Boolean) extends PgShadowFieldValue("userepl", value)
  case class usebypassrls(override val value: Boolean) extends PgShadowFieldValue("usebypassrls", value)
  case class passwd(override val value: Option[String]) extends PgShadowFieldValue("passwd", value)
  case class valuntil(override val value: Option[LocalDateTime]) extends PgShadowFieldValue("valuntil", value)
  case class useconfig(override val value: Option[Array[String]]) extends PgShadowFieldValue("useconfig", value)
}
