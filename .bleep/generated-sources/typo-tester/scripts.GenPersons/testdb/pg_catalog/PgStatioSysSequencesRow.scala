package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatioSysSequencesRow(
  /** Points to [[testdb.pg_catalog.PgStatioAllSequencesRow.relid]] */
  relid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllSequencesRow.schemaname]] */
  schemaname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllSequencesRow.relname]] */
  relname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllSequencesRow.blksRead]] */
  blksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllSequencesRow.blksHit]] */
  blksHit: Option[Long]
)

object PgStatioSysSequencesRow {
  implicit val rowParser: RowParser[PgStatioSysSequencesRow] = { row =>
    Success(
      PgStatioSysSequencesRow(
        relid = row[Option[Long]]("relid"),
        schemaname = row[Option[String]]("schemaname"),
        relname = row[Option[String]]("relname"),
        blksRead = row[Option[Long]]("blks_read"),
        blksHit = row[Option[Long]]("blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioSysSequencesRow] = new OFormat[PgStatioSysSequencesRow]{
    override def writes(o: PgStatioSysSequencesRow): JsObject =
      Json.obj(
        "relid" -> o.relid,
      "schemaname" -> o.schemaname,
      "relname" -> o.relname,
      "blks_read" -> o.blksRead,
      "blks_hit" -> o.blksHit
      )

    override def reads(json: JsValue): JsResult[PgStatioSysSequencesRow] = {
      JsResult.fromTry(
        Try(
          PgStatioSysSequencesRow(
            relid = json.\("relid").toOption.map(_.as[Long]),
            schemaname = json.\("schemaname").toOption.map(_.as[String]),
            relname = json.\("relname").toOption.map(_.as[String]),
            blksRead = json.\("blks_read").toOption.map(_.as[Long]),
            blksHit = json.\("blks_hit").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
