package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgForeignTableColumnsRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  nspname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  relname: String,
  /** Points to [[testdb.pg_catalog.PgAttributeRow.attname]] */
  attname: String,
  /** Points to [[testdb.pg_catalog.PgAttributeRow.attfdwoptions]] */
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
