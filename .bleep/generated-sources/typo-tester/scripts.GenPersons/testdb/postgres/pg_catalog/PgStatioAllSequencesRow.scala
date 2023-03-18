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

case class PgStatioAllSequencesRow(
  /** Points to [[PgClassRow.oid]] */
  relid: Long,
  /** Points to [[PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[PgClassRow.relname]] */
  relname: String,
  blksRead: /* unknown nullability */ Option[Long],
  blksHit: /* unknown nullability */ Option[Long]
)

object PgStatioAllSequencesRow {
  implicit val rowParser: RowParser[PgStatioAllSequencesRow] = { row =>
    Success(
      PgStatioAllSequencesRow(
        relid = row[Long]("relid"),
        schemaname = row[String]("schemaname"),
        relname = row[String]("relname"),
        blksRead = row[/* unknown nullability */ Option[Long]]("blks_read"),
        blksHit = row[/* unknown nullability */ Option[Long]]("blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioAllSequencesRow] = new OFormat[PgStatioAllSequencesRow]{
    override def writes(o: PgStatioAllSequencesRow): JsObject =
      Json.obj(
        "relid" -> o.relid,
      "schemaname" -> o.schemaname,
      "relname" -> o.relname,
      "blks_read" -> o.blksRead,
      "blks_hit" -> o.blksHit
      )

    override def reads(json: JsValue): JsResult[PgStatioAllSequencesRow] = {
      JsResult.fromTry(
        Try(
          PgStatioAllSequencesRow(
            relid = json.\("relid").as[Long],
            schemaname = json.\("schemaname").as[String],
            relname = json.\("relname").as[String],
            blksRead = json.\("blks_read").toOption.map(_.as[Long]),
            blksHit = json.\("blks_hit").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
