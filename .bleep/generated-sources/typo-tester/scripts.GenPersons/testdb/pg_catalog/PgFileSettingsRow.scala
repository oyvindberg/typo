package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgFileSettingsRow(
  sourcefile: /* unknown nullability */ Option[String],
  sourceline: /* unknown nullability */ Option[Int],
  seqno: /* unknown nullability */ Option[Int],
  name: /* unknown nullability */ Option[String],
  setting: /* unknown nullability */ Option[String],
  applied: /* unknown nullability */ Option[Boolean],
  error: /* unknown nullability */ Option[String]
)

object PgFileSettingsRow {
  implicit val rowParser: RowParser[PgFileSettingsRow] = { row =>
    Success(
      PgFileSettingsRow(
        sourcefile = row[/* unknown nullability */ Option[String]]("sourcefile"),
        sourceline = row[/* unknown nullability */ Option[Int]]("sourceline"),
        seqno = row[/* unknown nullability */ Option[Int]]("seqno"),
        name = row[/* unknown nullability */ Option[String]]("name"),
        setting = row[/* unknown nullability */ Option[String]]("setting"),
        applied = row[/* unknown nullability */ Option[Boolean]]("applied"),
        error = row[/* unknown nullability */ Option[String]]("error")
      )
    )
  }

  implicit val oFormat: OFormat[PgFileSettingsRow] = Json.format
}
