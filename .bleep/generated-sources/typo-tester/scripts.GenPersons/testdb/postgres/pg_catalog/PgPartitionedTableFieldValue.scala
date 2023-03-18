package testdb
package postgres
package pg_catalog



sealed abstract class PgPartitionedTableFieldValue[T](val name: String, val value: T)

object PgPartitionedTableFieldValue {
  case class partrelid(override val value: Long) extends PgPartitionedTableFieldValue("partrelid", value)
  case class partstrat(override val value: String) extends PgPartitionedTableFieldValue("partstrat", value)
  case class partnatts(override val value: Short) extends PgPartitionedTableFieldValue("partnatts", value)
  case class partdefid(override val value: Long) extends PgPartitionedTableFieldValue("partdefid", value)
  case class partattrs(override val value: String) extends PgPartitionedTableFieldValue("partattrs", value)
  case class partclass(override val value: String) extends PgPartitionedTableFieldValue("partclass", value)
  case class partcollation(override val value: String) extends PgPartitionedTableFieldValue("partcollation", value)
  case class partexprs(override val value: Option[String]) extends PgPartitionedTableFieldValue("partexprs", value)
}
