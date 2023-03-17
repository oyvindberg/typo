package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgCursorsRow(
  name: /* unknown nullability */ Option[String],
  statement: /* unknown nullability */ Option[String],
  isHoldable: /* unknown nullability */ Option[Boolean],
  isBinary: /* unknown nullability */ Option[Boolean],
  isScrollable: /* unknown nullability */ Option[Boolean],
  creationTime: /* unknown nullability */ Option[LocalDateTime]
)

object PgCursorsRow {
  implicit val rowParser: RowParser[PgCursorsRow] = { row =>
    Success(
      PgCursorsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        statement = row[/* unknown nullability */ Option[String]]("statement"),
        isHoldable = row[/* unknown nullability */ Option[Boolean]]("is_holdable"),
        isBinary = row[/* unknown nullability */ Option[Boolean]]("is_binary"),
        isScrollable = row[/* unknown nullability */ Option[Boolean]]("is_scrollable"),
        creationTime = row[/* unknown nullability */ Option[LocalDateTime]]("creation_time")
      )
    )
  }

  implicit val oFormat: OFormat[PgCursorsRow] = Json.format
}
