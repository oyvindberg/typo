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

case class PgReplicationSlotsRow(
  slotName: /* unknown nullability */ Option[String],
  plugin: /* unknown nullability */ Option[String],
  slotType: /* unknown nullability */ Option[String],
  datoid: /* unknown nullability */ Option[Long],
  /** Points to [[testdb.pg_catalog.PgDatabaseRow.datname]] */
  database: String,
  temporary: /* unknown nullability */ Option[Boolean],
  active: /* unknown nullability */ Option[Boolean],
  activePid: /* unknown nullability */ Option[Int],
  xmin: /* unknown nullability */ Option[/* xid */ String],
  catalogXmin: /* unknown nullability */ Option[/* xid */ String],
  restartLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  confirmedFlushLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  walStatus: /* unknown nullability */ Option[String],
  safeWalSize: /* unknown nullability */ Option[Long],
  twoPhase: /* unknown nullability */ Option[Boolean]
)

object PgReplicationSlotsRow {
  implicit val rowParser: RowParser[PgReplicationSlotsRow] = { row =>
    Success(
      PgReplicationSlotsRow(
        slotName = row[/* unknown nullability */ Option[String]]("slot_name"),
        plugin = row[/* unknown nullability */ Option[String]]("plugin"),
        slotType = row[/* unknown nullability */ Option[String]]("slot_type"),
        datoid = row[/* unknown nullability */ Option[Long]]("datoid"),
        database = row[String]("database"),
        temporary = row[/* unknown nullability */ Option[Boolean]]("temporary"),
        active = row[/* unknown nullability */ Option[Boolean]]("active"),
        activePid = row[/* unknown nullability */ Option[Int]]("active_pid"),
        xmin = row[/* unknown nullability */ Option[/* xid */ String]]("xmin"),
        catalogXmin = row[/* unknown nullability */ Option[/* xid */ String]]("catalog_xmin"),
        restartLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("restart_lsn"),
        confirmedFlushLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("confirmed_flush_lsn"),
        walStatus = row[/* unknown nullability */ Option[String]]("wal_status"),
        safeWalSize = row[/* unknown nullability */ Option[Long]]("safe_wal_size"),
        twoPhase = row[/* unknown nullability */ Option[Boolean]]("two_phase")
      )
    )
  }

  implicit val oFormat: OFormat[PgReplicationSlotsRow] = new OFormat[PgReplicationSlotsRow]{
    override def writes(o: PgReplicationSlotsRow): JsObject =
      Json.obj(
        "slot_name" -> o.slotName,
      "plugin" -> o.plugin,
      "slot_type" -> o.slotType,
      "datoid" -> o.datoid,
      "database" -> o.database,
      "temporary" -> o.temporary,
      "active" -> o.active,
      "active_pid" -> o.activePid,
      "xmin" -> o.xmin,
      "catalog_xmin" -> o.catalogXmin,
      "restart_lsn" -> o.restartLsn,
      "confirmed_flush_lsn" -> o.confirmedFlushLsn,
      "wal_status" -> o.walStatus,
      "safe_wal_size" -> o.safeWalSize,
      "two_phase" -> o.twoPhase
      )

    override def reads(json: JsValue): JsResult[PgReplicationSlotsRow] = {
      JsResult.fromTry(
        Try(
          PgReplicationSlotsRow(
            slotName = json.\("slot_name").toOption.map(_.as[String]),
            plugin = json.\("plugin").toOption.map(_.as[String]),
            slotType = json.\("slot_type").toOption.map(_.as[String]),
            datoid = json.\("datoid").toOption.map(_.as[Long]),
            database = json.\("database").as[String],
            temporary = json.\("temporary").toOption.map(_.as[Boolean]),
            active = json.\("active").toOption.map(_.as[Boolean]),
            activePid = json.\("active_pid").toOption.map(_.as[Int]),
            xmin = json.\("xmin").toOption.map(_.as[/* xid */ String]),
            catalogXmin = json.\("catalog_xmin").toOption.map(_.as[/* xid */ String]),
            restartLsn = json.\("restart_lsn").toOption.map(_.as[/* pg_lsn */ String]),
            confirmedFlushLsn = json.\("confirmed_flush_lsn").toOption.map(_.as[/* pg_lsn */ String]),
            walStatus = json.\("wal_status").toOption.map(_.as[String]),
            safeWalSize = json.\("safe_wal_size").toOption.map(_.as[Long]),
            twoPhase = json.\("two_phase").toOption.map(_.as[Boolean])
          )
        )
      )
    }
  }
}
