package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgViewsRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  viewname: String,
  viewowner: /* unknown nullability */ Option[String],
  definition: /* unknown nullability */ Option[String]
)

object PgViewsRow {
  implicit val rowParser: RowParser[PgViewsRow] = { row =>
    Success(
      PgViewsRow(
        schemaname = row[String]("schemaname"),
        viewname = row[String]("viewname"),
        viewowner = row[/* unknown nullability */ Option[String]]("viewowner"),
        definition = row[/* unknown nullability */ Option[String]]("definition")
      )
    )
  }

  implicit val oFormat: OFormat[PgViewsRow] = Json.format
}
