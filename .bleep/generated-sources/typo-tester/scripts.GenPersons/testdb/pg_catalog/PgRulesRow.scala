package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgRulesRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  tablename: String,
  /** Points to [[testdb.pg_catalog.PgRewriteRow.rulename]] */
  rulename: String,
  definition: /* unknown nullability */ Option[String]
)

object PgRulesRow {
  implicit val rowParser: RowParser[PgRulesRow] = { row =>
    Success(
      PgRulesRow(
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename"),
        rulename = row[String]("rulename"),
        definition = row[/* unknown nullability */ Option[String]]("definition")
      )
    )
  }

  implicit val oFormat: OFormat[PgRulesRow] = Json.format
}
