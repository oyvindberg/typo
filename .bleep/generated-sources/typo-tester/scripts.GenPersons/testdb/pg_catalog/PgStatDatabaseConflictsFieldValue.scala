package testdb.pg_catalog



sealed abstract class PgStatDatabaseConflictsFieldValue[T](val name: String, val value: T)

object PgStatDatabaseConflictsFieldValue {
  case class datid(override val value: Long) extends PgStatDatabaseConflictsFieldValue("datid", value)
  case class datname(override val value: String) extends PgStatDatabaseConflictsFieldValue("datname", value)
  case class conflTablespace(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseConflictsFieldValue("confl_tablespace", value)
  case class conflLock(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseConflictsFieldValue("confl_lock", value)
  case class conflSnapshot(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseConflictsFieldValue("confl_snapshot", value)
  case class conflBufferpin(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseConflictsFieldValue("confl_bufferpin", value)
  case class conflDeadlock(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseConflictsFieldValue("confl_deadlock", value)
}
