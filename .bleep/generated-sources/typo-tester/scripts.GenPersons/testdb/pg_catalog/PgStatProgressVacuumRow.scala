package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatProgressVacuumRow(
  pid: /* unknown nullability */ Option[Int],
  datid: /* unknown nullability */ Option[Long],
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

  implicit val oFormat: OFormat[PgStatProgressVacuumRow] = Json.format
}
