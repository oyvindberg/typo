package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgMatviewsRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  matviewname: String,
  matviewowner: /* unknown nullability */ Option[String],
  /** Points to [[testdb.pg_catalog.PgTablespaceRow.spcname]] */
  tablespace: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relhasindex]] */
  hasindexes: Boolean,
  /** Points to [[testdb.pg_catalog.PgClassRow.relispopulated]] */
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
