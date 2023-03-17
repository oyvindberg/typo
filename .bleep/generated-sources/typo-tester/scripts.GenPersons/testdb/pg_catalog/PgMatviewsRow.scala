package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgMatviewsRow(
  schemaname: String,
  matviewname: String,
  matviewowner: /* unknown nullability */ Option[String],
  tablespace: String,
  hasindexes: Boolean,
  ispopulated: Boolean,
  definition: /* unknown nullability */ Option[String]
)

object PgMatviewsRow {
  implicit val rowParser: RowParser[PgMatviewsRow] = { row =>
    Success(
      PgMatviewsRow(
        schemaname = row[String]("schemaname"),
        matviewname = row[String]("matviewname"),
        matviewowner = row[/* unknown nullability */ Option[String]]("matviewowner"),
        tablespace = row[String]("tablespace"),
        hasindexes = row[Boolean]("hasindexes"),
        ispopulated = row[Boolean]("ispopulated"),
        definition = row[/* unknown nullability */ Option[String]]("definition")
      )
    )
  }

  implicit val oFormat: OFormat[PgMatviewsRow] = Json.format
}
