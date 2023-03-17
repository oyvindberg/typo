package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgIndexesRow(
  schemaname: String,
  tablename: String,
  indexname: String,
  tablespace: String,
  indexdef: /* unknown nullability */ Option[String]
)

object PgIndexesRow {
  implicit val rowParser: RowParser[PgIndexesRow] = { row =>
    Success(
      PgIndexesRow(
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename"),
        indexname = row[String]("indexname"),
        tablespace = row[String]("tablespace"),
        indexdef = row[/* unknown nullability */ Option[String]]("indexdef")
      )
    )
  }

  implicit val oFormat: OFormat[PgIndexesRow] = Json.format
}
