package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgForeignTableColumnsRow(
  nspname: String,
  relname: String,
  attname: String,
  attfdwoptions: Option[Array[String]]
)

object PgForeignTableColumnsRow {
  implicit val rowParser: RowParser[PgForeignTableColumnsRow] = { row =>
    Success(
      PgForeignTableColumnsRow(
        nspname = row[String]("nspname"),
        relname = row[String]("relname"),
        attname = row[String]("attname"),
        attfdwoptions = row[Option[Array[String]]]("attfdwoptions")
      )
    )
  }

  implicit val oFormat: OFormat[PgForeignTableColumnsRow] = Json.format
}
