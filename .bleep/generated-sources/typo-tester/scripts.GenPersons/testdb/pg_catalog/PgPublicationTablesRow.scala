package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgPublicationTablesRow(
  pubname: String,
  schemaname: String,
  tablename: String
)

object PgPublicationTablesRow {
  implicit val rowParser: RowParser[PgPublicationTablesRow] = { row =>
    Success(
      PgPublicationTablesRow(
        pubname = row[String]("pubname"),
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename")
      )
    )
  }

  implicit val oFormat: OFormat[PgPublicationTablesRow] = Json.format
}
