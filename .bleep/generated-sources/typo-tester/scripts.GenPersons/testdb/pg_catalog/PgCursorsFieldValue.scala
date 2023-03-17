package testdb.pg_catalog

import java.time.LocalDateTime

sealed abstract class PgCursorsFieldValue[T](val name: String, val value: T)

object PgCursorsFieldValue {
  case class name(override val value: /* unknown nullability */ Option[String]) extends PgCursorsFieldValue("name", value)
  case class statement(override val value: /* unknown nullability */ Option[String]) extends PgCursorsFieldValue("statement", value)
  case class isHoldable(override val value: /* unknown nullability */ Option[Boolean]) extends PgCursorsFieldValue("is_holdable", value)
  case class isBinary(override val value: /* unknown nullability */ Option[Boolean]) extends PgCursorsFieldValue("is_binary", value)
  case class isScrollable(override val value: /* unknown nullability */ Option[Boolean]) extends PgCursorsFieldValue("is_scrollable", value)
  case class creationTime(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgCursorsFieldValue("creation_time", value)
}
