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

case class PgStatioSysTablesRow(
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.relid]] */
  relid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.schemaname]] */
  schemaname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.relname]] */
  relname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.heapBlksRead]] */
  heapBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.heapBlksHit]] */
  heapBlksHit: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.idxBlksRead]] */
  idxBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.idxBlksHit]] */
  idxBlksHit: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.toastBlksRead]] */
  toastBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.toastBlksHit]] */
  toastBlksHit: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.tidxBlksRead]] */
  tidxBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.tidxBlksHit]] */
  tidxBlksHit: Option[Long]
)

object PgStatioSysTablesRow {
  implicit val rowParser: RowParser[PgStatioSysTablesRow] = { row =>
    Success(
      PgStatioSysTablesRow(
        relid = row[Option[Long]]("relid"),
        schemaname = row[Option[String]]("schemaname"),
        relname = row[Option[String]]("relname"),
        heapBlksRead = row[Option[Long]]("heap_blks_read"),
        heapBlksHit = row[Option[Long]]("heap_blks_hit"),
        idxBlksRead = row[Option[Long]]("idx_blks_read"),
        idxBlksHit = row[Option[Long]]("idx_blks_hit"),
        toastBlksRead = row[Option[Long]]("toast_blks_read"),
        toastBlksHit = row[Option[Long]]("toast_blks_hit"),
        tidxBlksRead = row[Option[Long]]("tidx_blks_read"),
        tidxBlksHit = row[Option[Long]]("tidx_blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioSysTablesRow] = new OFormat[PgStatioSysTablesRow]{
    override def writes(o: PgStatioSysTablesRow): JsObject =
      Json.obj(
        "relid" -> o.relid,
      "schemaname" -> o.schemaname,
      "relname" -> o.relname,
      "heap_blks_read" -> o.heapBlksRead,
      "heap_blks_hit" -> o.heapBlksHit,
      "idx_blks_read" -> o.idxBlksRead,
      "idx_blks_hit" -> o.idxBlksHit,
      "toast_blks_read" -> o.toastBlksRead,
      "toast_blks_hit" -> o.toastBlksHit,
      "tidx_blks_read" -> o.tidxBlksRead,
      "tidx_blks_hit" -> o.tidxBlksHit
      )

    override def reads(json: JsValue): JsResult[PgStatioSysTablesRow] = {
      JsResult.fromTry(
        Try(
          PgStatioSysTablesRow(
            relid = json.\("relid").toOption.map(_.as[Long]),
            schemaname = json.\("schemaname").toOption.map(_.as[String]),
            relname = json.\("relname").toOption.map(_.as[String]),
            heapBlksRead = json.\("heap_blks_read").toOption.map(_.as[Long]),
            heapBlksHit = json.\("heap_blks_hit").toOption.map(_.as[Long]),
            idxBlksRead = json.\("idx_blks_read").toOption.map(_.as[Long]),
            idxBlksHit = json.\("idx_blks_hit").toOption.map(_.as[Long]),
            toastBlksRead = json.\("toast_blks_read").toOption.map(_.as[Long]),
            toastBlksHit = json.\("toast_blks_hit").toOption.map(_.as[Long]),
            tidxBlksRead = json.\("tidx_blks_read").toOption.map(_.as[Long]),
            tidxBlksHit = json.\("tidx_blks_hit").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
