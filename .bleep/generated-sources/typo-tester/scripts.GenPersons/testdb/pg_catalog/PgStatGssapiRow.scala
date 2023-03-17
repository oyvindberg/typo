package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatGssapiRow(
  pid: /* unknown nullability */ Option[Int],
  gssAuthenticated: /* unknown nullability */ Option[Boolean],
  principal: /* unknown nullability */ Option[String],
  encrypted: /* unknown nullability */ Option[Boolean]
)

object PgStatGssapiRow {
  implicit val rowParser: RowParser[PgStatGssapiRow] = { row =>
    Success(
      PgStatGssapiRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        gssAuthenticated = row[/* unknown nullability */ Option[Boolean]]("gss_authenticated"),
        principal = row[/* unknown nullability */ Option[String]]("principal"),
        encrypted = row[/* unknown nullability */ Option[Boolean]]("encrypted")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatGssapiRow] = Json.format
}
