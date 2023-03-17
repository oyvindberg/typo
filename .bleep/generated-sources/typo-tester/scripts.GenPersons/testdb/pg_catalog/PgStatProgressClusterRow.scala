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

case class PgStatProgressClusterRow(
  pid: /* unknown nullability */ Option[Int],
  datid: /* unknown nullability */ Option[Long],
  /** Points to [[testdb.pg_catalog.PgDatabaseRow.datname]] */
  datname: String,
  relid: /* unknown nullability */ Option[Long],
  command: /* unknown nullability */ Option[String],
  phase: /* unknown nullability */ Option[String],
  clusterIndexRelid: /* unknown nullability */ Option[Long],
  heapTuplesScanned: /* unknown nullability */ Option[Long],
  heapTuplesWritten: /* unknown nullability */ Option[Long],
  heapBlksTotal: /* unknown nullability */ Option[Long],
  heapBlksScanned: /* unknown nullability */ Option[Long],
  indexRebuildCount: /* unknown nullability */ Option[Long]
)

object PgStatProgressClusterRow {
  implicit val rowParser: RowParser[PgStatProgressClusterRow] = { row =>
    Success(
      PgStatProgressClusterRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        datid = row[/* unknown nullability */ Option[Long]]("datid"),
        datname = row[String]("datname"),
        relid = row[/* unknown nullability */ Option[Long]]("relid"),
        command = row[/* unknown nullability */ Option[String]]("command"),
        phase = row[/* unknown nullability */ Option[String]]("phase"),
        clusterIndexRelid = row[/* unknown nullability */ Option[Long]]("cluster_index_relid"),
        heapTuplesScanned = row[/* unknown nullability */ Option[Long]]("heap_tuples_scanned"),
        heapTuplesWritten = row[/* unknown nullability */ Option[Long]]("heap_tuples_written"),
        heapBlksTotal = row[/* unknown nullability */ Option[Long]]("heap_blks_total"),
        heapBlksScanned = row[/* unknown nullability */ Option[Long]]("heap_blks_scanned"),
        indexRebuildCount = row[/* unknown nullability */ Option[Long]]("index_rebuild_count")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatProgressClusterRow] = new OFormat[PgStatProgressClusterRow]{
    override def writes(o: PgStatProgressClusterRow): JsObject =
      Json.obj(
        "pid" -> o.pid,
      "datid" -> o.datid,
      "datname" -> o.datname,
      "relid" -> o.relid,
      "command" -> o.command,
      "phase" -> o.phase,
      "cluster_index_relid" -> o.clusterIndexRelid,
      "heap_tuples_scanned" -> o.heapTuplesScanned,
      "heap_tuples_written" -> o.heapTuplesWritten,
      "heap_blks_total" -> o.heapBlksTotal,
      "heap_blks_scanned" -> o.heapBlksScanned,
      "index_rebuild_count" -> o.indexRebuildCount
      )

    override def reads(json: JsValue): JsResult[PgStatProgressClusterRow] = {
      JsResult.fromTry(
        Try(
          PgStatProgressClusterRow(
            pid = json.\("pid").toOption.map(_.as[Int]),
            datid = json.\("datid").toOption.map(_.as[Long]),
            datname = json.\("datname").as[String],
            relid = json.\("relid").toOption.map(_.as[Long]),
            command = json.\("command").toOption.map(_.as[String]),
            phase = json.\("phase").toOption.map(_.as[String]),
            clusterIndexRelid = json.\("cluster_index_relid").toOption.map(_.as[Long]),
            heapTuplesScanned = json.\("heap_tuples_scanned").toOption.map(_.as[Long]),
            heapTuplesWritten = json.\("heap_tuples_written").toOption.map(_.as[Long]),
            heapBlksTotal = json.\("heap_blks_total").toOption.map(_.as[Long]),
            heapBlksScanned = json.\("heap_blks_scanned").toOption.map(_.as[Long]),
            indexRebuildCount = json.\("index_rebuild_count").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
