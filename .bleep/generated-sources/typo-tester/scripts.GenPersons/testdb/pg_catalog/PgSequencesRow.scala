package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgSequencesRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  sequencename: String,
  sequenceowner: /* unknown nullability */ Option[String],
  dataType: /* unknown nullability */ Option[/* regtype */ String],
  /** Points to [[testdb.pg_catalog.PgSequenceRow.seqstart]] */
  startValue: Long,
  /** Points to [[testdb.pg_catalog.PgSequenceRow.seqmin]] */
  minValue: Long,
  /** Points to [[testdb.pg_catalog.PgSequenceRow.seqmax]] */
  maxValue: Long,
  /** Points to [[testdb.pg_catalog.PgSequenceRow.seqincrement]] */
  incrementBy: Long,
  /** Points to [[testdb.pg_catalog.PgSequenceRow.seqcycle]] */
  cycle: Boolean,
  /** Points to [[testdb.pg_catalog.PgSequenceRow.seqcache]] */
  cacheSize: Long,
  lastValue: /* unknown nullability */ Option[Long]
)

object PgSequencesRow {
  implicit val rowParser: RowParser[PgSequencesRow] = { row =>
    Success(
      PgSequencesRow(
        schemaname = row[String]("schemaname"),
        sequencename = row[String]("sequencename"),
        sequenceowner = row[/* unknown nullability */ Option[String]]("sequenceowner"),
        dataType = row[/* unknown nullability */ Option[/* regtype */ String]]("data_type"),
        startValue = row[Long]("start_value"),
        minValue = row[Long]("min_value"),
        maxValue = row[Long]("max_value"),
        incrementBy = row[Long]("increment_by"),
        cycle = row[Boolean]("cycle"),
        cacheSize = row[Long]("cache_size"),
        lastValue = row[/* unknown nullability */ Option[Long]]("last_value")
      )
    )
  }

  implicit val oFormat: OFormat[PgSequencesRow] = Json.format
}
