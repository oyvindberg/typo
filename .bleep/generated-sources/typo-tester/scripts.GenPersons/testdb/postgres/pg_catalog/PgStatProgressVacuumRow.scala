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

case class PgStatProgressVacuumRow(
  pid: /* unknown nullability */ Option[Int],
  datid: /* unknown nullability */ Option[Long],
  /** Points to [[PgDatabaseRow.datname]] */
  datname: String,
  relid: /* unknown nullability */ Option[Long],
  phase: /* unknown nullability */ Option[String],
  heapBlksTotal: /* unknown nullability */ Option[Long],
  heapBlksScanned: /* unknown nullability */ Option[Long],
  heapBlksVacuumed: /* unknown nullability */ Option[Long],
  indexVacuumCount: /* unknown nullability */ Option[Long],
  maxDeadTuples: /* unknown nullability */ Option[Long],
  numDeadTuples: /* unknown nullability */ Option[Long]
)

object PgStatProgressVacuumRow {
  implicit val rowParser: RowParser[PgStatProgressVacuumRow] = { row =>
    Success(
      PgStatProgressVacuumRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        datid = row[/* unknown nullability */ Option[Long]]("datid"),
        datname = row[String]("datname"),
        relid = row[/* unknown nullability */ Option[Long]]("relid"),
        phase = row[/* unknown nullability */ Option[String]]("phase"),
        heapBlksTotal = row[/* unknown nullability */ Option[Long]]("heap_blks_total"),
        heapBlksScanned = row[/* unknown nullability */ Option[Long]]("heap_blks_scanned"),
        heapBlksVacuumed = row[/* unknown nullability */ Option[Long]]("heap_blks_vacuumed"),
        indexVacuumCount = row[/* unknown nullability */ Option[Long]]("index_vacuum_count"),
        maxDeadTuples = row[/* unknown nullability */ Option[Long]]("max_dead_tuples"),
        numDeadTuples = row[/* unknown nullability */ Option[Long]]("num_dead_tuples")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatProgressVacuumRow] = new OFormat[PgStatProgressVacuumRow]{
    override def writes(o: PgStatProgressVacuumRow): JsObject =
      Json.obj(
        "pid" -> o.pid,
      "datid" -> o.datid,
      "datname" -> o.datname,
      "relid" -> o.relid,
      "phase" -> o.phase,
      "heap_blks_total" -> o.heapBlksTotal,
      "heap_blks_scanned" -> o.heapBlksScanned,
      "heap_blks_vacuumed" -> o.heapBlksVacuumed,
      "index_vacuum_count" -> o.indexVacuumCount,
      "max_dead_tuples" -> o.maxDeadTuples,
      "num_dead_tuples" -> o.numDeadTuples
      )

    override def reads(json: JsValue): JsResult[PgStatProgressVacuumRow] = {
      JsResult.fromTry(
        Try(
          PgStatProgressVacuumRow(
            pid = json.\("pid").toOption.map(_.as[Int]),
            datid = json.\("datid").toOption.map(_.as[Long]),
            datname = json.\("datname").as[String],
            relid = json.\("relid").toOption.map(_.as[Long]),
            phase = json.\("phase").toOption.map(_.as[String]),
            heapBlksTotal = json.\("heap_blks_total").toOption.map(_.as[Long]),
            heapBlksScanned = json.\("heap_blks_scanned").toOption.map(_.as[Long]),
            heapBlksVacuumed = json.\("heap_blks_vacuumed").toOption.map(_.as[Long]),
            indexVacuumCount = json.\("index_vacuum_count").toOption.map(_.as[Long]),
            maxDeadTuples = json.\("max_dead_tuples").toOption.map(_.as[Long]),
            numDeadTuples = json.\("num_dead_tuples").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
