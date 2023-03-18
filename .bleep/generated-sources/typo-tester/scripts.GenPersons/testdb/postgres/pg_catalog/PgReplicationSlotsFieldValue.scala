package testdb
package postgres
package pg_catalog



sealed abstract class PgReplicationSlotsFieldValue[T](val name: String, val value: T)

object PgReplicationSlotsFieldValue {
  case class slotName(override val value: /* unknown nullability */ Option[String]) extends PgReplicationSlotsFieldValue("slot_name", value)
  case class plugin(override val value: /* unknown nullability */ Option[String]) extends PgReplicationSlotsFieldValue("plugin", value)
  case class slotType(override val value: /* unknown nullability */ Option[String]) extends PgReplicationSlotsFieldValue("slot_type", value)
  case class datoid(override val value: /* unknown nullability */ Option[Long]) extends PgReplicationSlotsFieldValue("datoid", value)
  case class database(override val value: String) extends PgReplicationSlotsFieldValue("database", value)
  case class temporary(override val value: /* unknown nullability */ Option[Boolean]) extends PgReplicationSlotsFieldValue("temporary", value)
  case class active(override val value: /* unknown nullability */ Option[Boolean]) extends PgReplicationSlotsFieldValue("active", value)
  case class activePid(override val value: /* unknown nullability */ Option[Int]) extends PgReplicationSlotsFieldValue("active_pid", value)
  case class xmin(override val value: /* unknown nullability */ Option[/* xid */ String]) extends PgReplicationSlotsFieldValue("xmin", value)
  case class catalogXmin(override val value: /* unknown nullability */ Option[/* xid */ String]) extends PgReplicationSlotsFieldValue("catalog_xmin", value)
  case class restartLsn(override val value: /* unknown nullability */ Option[/* pg_lsn */ String]) extends PgReplicationSlotsFieldValue("restart_lsn", value)
  case class confirmedFlushLsn(override val value: /* unknown nullability */ Option[/* pg_lsn */ String]) extends PgReplicationSlotsFieldValue("confirmed_flush_lsn", value)
  case class walStatus(override val value: /* unknown nullability */ Option[String]) extends PgReplicationSlotsFieldValue("wal_status", value)
  case class safeWalSize(override val value: /* unknown nullability */ Option[Long]) extends PgReplicationSlotsFieldValue("safe_wal_size", value)
  case class twoPhase(override val value: /* unknown nullability */ Option[Boolean]) extends PgReplicationSlotsFieldValue("two_phase", value)
}
