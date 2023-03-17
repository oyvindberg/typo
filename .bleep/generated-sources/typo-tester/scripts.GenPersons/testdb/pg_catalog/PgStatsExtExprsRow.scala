package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatsExtExprsRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  tablename: String,
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  statisticsSchemaname: String,
  /** Points to [[testdb.pg_catalog.PgStatisticExtRow.stxname]] */
  statisticsName: String,
  statisticsOwner: /* unknown nullability */ Option[String],
  expr: /* unknown nullability */ Option[String],
  nullFrac: /* unknown nullability */ Option[Float],
  avgWidth: /* unknown nullability */ Option[Int],
  nDistinct: /* unknown nullability */ Option[Float],
  mostCommonVals: /* unknown nullability */ Option[/* anyarray */ String],
  mostCommonFreqs: /* unknown nullability */ Option[Array[Float]],
  histogramBounds: /* unknown nullability */ Option[/* anyarray */ String],
  correlation: /* unknown nullability */ Option[Float],
  mostCommonElems: /* unknown nullability */ Option[/* anyarray */ String],
  mostCommonElemFreqs: /* unknown nullability */ Option[Array[Float]],
  elemCountHistogram: /* unknown nullability */ Option[Array[Float]]
)

object PgStatsExtExprsRow {
  implicit val rowParser: RowParser[PgStatsExtExprsRow] = { row =>
    Success(
      PgStatsExtExprsRow(
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename"),
        statisticsSchemaname = row[String]("statistics_schemaname"),
        statisticsName = row[String]("statistics_name"),
        statisticsOwner = row[/* unknown nullability */ Option[String]]("statistics_owner"),
        expr = row[/* unknown nullability */ Option[String]]("expr"),
        nullFrac = row[/* unknown nullability */ Option[Float]]("null_frac"),
        avgWidth = row[/* unknown nullability */ Option[Int]]("avg_width"),
        nDistinct = row[/* unknown nullability */ Option[Float]]("n_distinct"),
        mostCommonVals = row[/* unknown nullability */ Option[/* anyarray */ String]]("most_common_vals"),
        mostCommonFreqs = row[/* unknown nullability */ Option[Array[Float]]]("most_common_freqs"),
        histogramBounds = row[/* unknown nullability */ Option[/* anyarray */ String]]("histogram_bounds"),
        correlation = row[/* unknown nullability */ Option[Float]]("correlation"),
        mostCommonElems = row[/* unknown nullability */ Option[/* anyarray */ String]]("most_common_elems"),
        mostCommonElemFreqs = row[/* unknown nullability */ Option[Array[Float]]]("most_common_elem_freqs"),
        elemCountHistogram = row[/* unknown nullability */ Option[Array[Float]]]("elem_count_histogram")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatsExtExprsRow] = Json.format
}
