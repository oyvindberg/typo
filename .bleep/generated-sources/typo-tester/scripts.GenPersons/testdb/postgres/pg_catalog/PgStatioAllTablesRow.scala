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

case class PgStatioAllTablesRow(
  /** Points to [[PgClassRow.oid]] */
  relid: Long,
  /** Points to [[PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[PgClassRow.relname]] */
  relname: String,
  heapBlksRead: /* unknown nullability */ Option[Long],
  heapBlksHit: /* unknown nullability */ Option[Long],
  idxBlksRead: /* unknown nullability */ Option[Long],
  idxBlksHit: /* unknown nullability */ Option[Long],
  toastBlksRead: /* unknown nullability */ Option[Long],
  toastBlksHit: /* unknown nullability */ Option[Long],
  tidxBlksRead: /* unknown nullability */ Option[Long],
  tidxBlksHit: /* unknown nullability */ Option[Long]
)

object PgStatioAllTablesRow {
  implicit val rowParser: RowParser[PgStatioAllTablesRow] = { row =>
    Success(
      PgStatioAllTablesRow(
        relid = row[Long]("relid"),
        schemaname = row[String]("schemaname"),
        relname = row[String]("relname"),
        heapBlksRead = row[/* unknown nullability */ Option[Long]]("heap_blks_read"),
        heapBlksHit = row[/* unknown nullability */ Option[Long]]("heap_blks_hit"),
        idxBlksRead = row[/* unknown nullability */ Option[Long]]("idx_blks_read"),
        idxBlksHit = row[/* unknown nullability */ Option[Long]]("idx_blks_hit"),
        toastBlksRead = row[/* unknown nullability */ Option[Long]]("toast_blks_read"),
        toastBlksHit = row[/* unknown nullability */ Option[Long]]("toast_blks_hit"),
        tidxBlksRead = row[/* unknown nullability */ Option[Long]]("tidx_blks_read"),
        tidxBlksHit = row[/* unknown nullability */ Option[Long]]("tidx_blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioAllTablesRow] = new OFormat[PgStatioAllTablesRow]{
    override def writes(o: PgStatioAllTablesRow): JsObject =
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

    override def reads(json: JsValue): JsResult[PgStatioAllTablesRow] = {
      JsResult.fromTry(
        Try(
          PgStatioAllTablesRow(
            relid = json.\("relid").as[Long],
            schemaname = json.\("schemaname").as[String],
            relname = json.\("relname").as[String],
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
