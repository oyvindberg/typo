package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgAvailableExtensionsRow(
  name: /* unknown nullability */ Option[String],
  defaultVersion: /* unknown nullability */ Option[String],
  installedVersion: String,
  comment: /* unknown nullability */ Option[String]
)

object PgAvailableExtensionsRow {
  implicit val rowParser: RowParser[PgAvailableExtensionsRow] = { row =>
    Success(
      PgAvailableExtensionsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        defaultVersion = row[/* unknown nullability */ Option[String]]("default_version"),
        installedVersion = row[String]("installed_version"),
        comment = row[/* unknown nullability */ Option[String]]("comment")
      )
    )
  }

  implicit val oFormat: OFormat[PgAvailableExtensionsRow] = Json.format
}
