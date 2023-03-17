package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatioUserIndexesRow(
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.relid]] */
  relid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.indexrelid]] */
  indexrelid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.schemaname]] */
  schemaname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.relname]] */
  relname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.indexrelname]] */
  indexrelname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.idxBlksRead]] */
  idxBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.idxBlksHit]] */
  idxBlksHit: Option[Long]
)

object PgStatioUserIndexesRow {
  implicit val rowParser: RowParser[PgStatioUserIndexesRow] = { row =>
    Success(
      PgStatioUserIndexesRow(
        relid = row[Option[Long]]("relid"),
        indexrelid = row[Option[Long]]("indexrelid"),
        schemaname = row[Option[String]]("schemaname"),
        relname = row[Option[String]]("relname"),
        indexrelname = row[Option[String]]("indexrelname"),
        idxBlksRead = row[Option[Long]]("idx_blks_read"),
        idxBlksHit = row[Option[Long]]("idx_blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioUserIndexesRow] = new OFormat[PgStatioUserIndexesRow]{
    override def writes(o: PgStatioUserIndexesRow): JsObject =
      Json.obj(
        "relid" -> o.relid,
      "indexrelid" -> o.indexrelid,
      "schemaname" -> o.schemaname,
      "relname" -> o.relname,
      "indexrelname" -> o.indexrelname,
      "idx_blks_read" -> o.idxBlksRead,
      "idx_blks_hit" -> o.idxBlksHit
      )

    override def reads(json: JsValue): JsResult[PgStatioUserIndexesRow] = {
      JsResult.fromTry(
        Try(
          PgStatioUserIndexesRow(
            relid = json.\("relid").toOption.map(_.as[Long]),
            indexrelid = json.\("indexrelid").toOption.map(_.as[Long]),
            schemaname = json.\("schemaname").toOption.map(_.as[String]),
            relname = json.\("relname").toOption.map(_.as[String]),
            indexrelname = json.\("indexrelname").toOption.map(_.as[String]),
            idxBlksRead = json.\("idx_blks_read").toOption.map(_.as[Long]),
            idxBlksHit = json.\("idx_blks_hit").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
