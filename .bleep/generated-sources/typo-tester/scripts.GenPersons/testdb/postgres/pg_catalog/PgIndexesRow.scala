package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgIndexesRow(
  /** Points to [[PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[PgClassRow.relname]] */
  tablename: String,
  /** Points to [[PgClassRow.relname]] */
  indexname: String,
  /** Points to [[PgTablespaceRow.spcname]] */
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

  implicit val oFormat: OFormat[PgIndexesRow] = new OFormat[PgIndexesRow]{
    override def writes(o: PgIndexesRow): JsObject =
      Json.obj(
        "schemaname" -> o.schemaname,
      "tablename" -> o.tablename,
      "indexname" -> o.indexname,
      "tablespace" -> o.tablespace,
      "indexdef" -> o.indexdef
      )

    override def reads(json: JsValue): JsResult[PgIndexesRow] = {
      JsResult.fromTry(
        Try(
          PgIndexesRow(
            schemaname = json.\("schemaname").as[String],
            tablename = json.\("tablename").as[String],
            indexname = json.\("indexname").as[String],
            tablespace = json.\("tablespace").as[String],
            indexdef = json.\("indexdef").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
