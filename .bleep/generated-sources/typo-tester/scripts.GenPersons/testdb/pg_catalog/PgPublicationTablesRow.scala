package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgPublicationTablesRow(
  /** Points to [[testdb.pg_catalog.PgPublicationRow.pubname]] */
  pubname: String,
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
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

  implicit val oFormat: OFormat[PgPublicationTablesRow] = new OFormat[PgPublicationTablesRow]{
    override def writes(o: PgPublicationTablesRow): JsObject =
      Json.obj(
        "pubname" -> o.pubname,
      "schemaname" -> o.schemaname,
      "tablename" -> o.tablename
      )

    override def reads(json: JsValue): JsResult[PgPublicationTablesRow] = {
      JsResult.fromTry(
        Try(
          PgPublicationTablesRow(
            pubname = json.\("pubname").as[String],
            schemaname = json.\("schemaname").as[String],
            tablename = json.\("tablename").as[String]
          )
        )
      )
    }
  }
}
