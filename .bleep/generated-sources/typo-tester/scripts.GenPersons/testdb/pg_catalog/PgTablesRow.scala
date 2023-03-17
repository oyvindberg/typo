package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgTablesRow(
  schemaname: String,
  tablename: String,
  tableowner: /* unknown nullability */ Option[String],
  tablespace: String,
  hasindexes: Boolean,
  hasrules: Boolean,
  hastriggers: Boolean,
  rowsecurity: Boolean
)

object PgTablesRow {
  implicit val rowParser: RowParser[PgTablesRow] = { row =>
    Success(
      PgTablesRow(
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename"),
        tableowner = row[/* unknown nullability */ Option[String]]("tableowner"),
        tablespace = row[String]("tablespace"),
        hasindexes = row[Boolean]("hasindexes"),
        hasrules = row[Boolean]("hasrules"),
        hastriggers = row[Boolean]("hastriggers"),
        rowsecurity = row[Boolean]("rowsecurity")
      )
    )
  }

  implicit val oFormat: OFormat[PgTablesRow] = Json.format
}
