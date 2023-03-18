package testdb
package postgres
package pg_catalog



sealed abstract class PgConstraintFieldValue[T](val name: String, val value: T)

object PgConstraintFieldValue {
  case class oid(override val value: PgConstraintId) extends PgConstraintFieldValue("oid", value)
  case class conname(override val value: String) extends PgConstraintFieldValue("conname", value)
  case class connamespace(override val value: Long) extends PgConstraintFieldValue("connamespace", value)
  case class contype(override val value: String) extends PgConstraintFieldValue("contype", value)
  case class condeferrable(override val value: Boolean) extends PgConstraintFieldValue("condeferrable", value)
  case class condeferred(override val value: Boolean) extends PgConstraintFieldValue("condeferred", value)
  case class convalidated(override val value: Boolean) extends PgConstraintFieldValue("convalidated", value)
  case class conrelid(override val value: Long) extends PgConstraintFieldValue("conrelid", value)
  case class contypid(override val value: Long) extends PgConstraintFieldValue("contypid", value)
  case class conindid(override val value: Long) extends PgConstraintFieldValue("conindid", value)
  case class conparentid(override val value: Long) extends PgConstraintFieldValue("conparentid", value)
  case class confrelid(override val value: Long) extends PgConstraintFieldValue("confrelid", value)
  case class confupdtype(override val value: String) extends PgConstraintFieldValue("confupdtype", value)
  case class confdeltype(override val value: String) extends PgConstraintFieldValue("confdeltype", value)
  case class confmatchtype(override val value: String) extends PgConstraintFieldValue("confmatchtype", value)
  case class conislocal(override val value: Boolean) extends PgConstraintFieldValue("conislocal", value)
  case class coninhcount(override val value: Int) extends PgConstraintFieldValue("coninhcount", value)
  case class connoinherit(override val value: Boolean) extends PgConstraintFieldValue("connoinherit", value)
  case class conkey(override val value: Option[Array[Short]]) extends PgConstraintFieldValue("conkey", value)
  case class confkey(override val value: Option[Array[Short]]) extends PgConstraintFieldValue("confkey", value)
  case class conpfeqop(override val value: Option[Array[Long]]) extends PgConstraintFieldValue("conpfeqop", value)
  case class conppeqop(override val value: Option[Array[Long]]) extends PgConstraintFieldValue("conppeqop", value)
  case class conffeqop(override val value: Option[Array[Long]]) extends PgConstraintFieldValue("conffeqop", value)
  case class conexclop(override val value: Option[Array[Long]]) extends PgConstraintFieldValue("conexclop", value)
  case class conbin(override val value: Option[String]) extends PgConstraintFieldValue("conbin", value)
}
