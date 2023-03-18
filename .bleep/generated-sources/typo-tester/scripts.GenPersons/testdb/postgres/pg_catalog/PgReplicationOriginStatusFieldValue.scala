package testdb
package postgres
package pg_catalog



sealed abstract class PgReplicationOriginStatusFieldValue[T](val name: String, val value: T)

object PgReplicationOriginStatusFieldValue {
  case class localId(override val value: /* unknown nullability */ Option[Long]) extends PgReplicationOriginStatusFieldValue("local_id", value)
  case class externalId(override val value: /* unknown nullability */ Option[String]) extends PgReplicationOriginStatusFieldValue("external_id", value)
  case class remoteLsn(override val value: /* unknown nullability */ Option[/* pg_lsn */ String]) extends PgReplicationOriginStatusFieldValue("remote_lsn", value)
  case class localLsn(override val value: /* unknown nullability */ Option[/* pg_lsn */ String]) extends PgReplicationOriginStatusFieldValue("local_lsn", value)
}
