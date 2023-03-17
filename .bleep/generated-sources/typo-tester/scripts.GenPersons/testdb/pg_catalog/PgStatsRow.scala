package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatsRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  tablename: String,
  /** Points to [[testdb.pg_catalog.PgAttributeRow.attname]] */
  attname: String,
  /** Points to [[testdb.pg_catalog.PgStatisticRow.stainherit]] */
  inherited: Boolean,
  /** Points to [[testdb.pg_catalog.PgStatisticRow.stanullfrac]] */
  nullFrac: Float,
  /** Points to [[testdb.pg_catalog.PgStatisticRow.stawidth]] */
  avgWidth: Int,
  /** Points to [[testdb.pg_catalog.PgStatisticRow.stadistinct]] */
  nDistinct: Float,
  mostCommonVals: /* unknown nullability */ Option[/* anyarray */ String],
  mostCommonFreqs: /* unknown nullability */ Option[Array[Float]],
  histogramBounds: /* unknown nullability */ Option[/* anyarray */ String],
  correlation: /* unknown nullability */ Option[Float],
  mostCommonElems: /* unknown nullability */ Option[/* anyarray */ String],
  mostCommonElemFreqs: /* unknown nullability */ Option[Array[Float]],
  elemCountHistogram: /* unknown nullability */ Option[Array[Float]]
)

object PgStatsRow {
  implicit val rowParser: RowParser[PgStatsRow] = { row =>
    Success(
      PgStatsRow(
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename"),
        attname = row[String]("attname"),
        inherited = row[Boolean]("inherited"),
        nullFrac = row[Float]("null_frac"),
        avgWidth = row[Int]("avg_width"),
        nDistinct = row[Float]("n_distinct"),
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

  implicit val oFormat: OFormat[PgStatsRow] = Json.format
}
