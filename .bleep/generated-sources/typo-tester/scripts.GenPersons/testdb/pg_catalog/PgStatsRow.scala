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

  implicit val oFormat: OFormat[PgStatsRow] = new OFormat[PgStatsRow]{
    override def writes(o: PgStatsRow): JsObject =
      Json.obj(
        "schemaname" -> o.schemaname,
      "tablename" -> o.tablename,
      "attname" -> o.attname,
      "inherited" -> o.inherited,
      "null_frac" -> o.nullFrac,
      "avg_width" -> o.avgWidth,
      "n_distinct" -> o.nDistinct,
      "most_common_vals" -> o.mostCommonVals,
      "most_common_freqs" -> o.mostCommonFreqs,
      "histogram_bounds" -> o.histogramBounds,
      "correlation" -> o.correlation,
      "most_common_elems" -> o.mostCommonElems,
      "most_common_elem_freqs" -> o.mostCommonElemFreqs,
      "elem_count_histogram" -> o.elemCountHistogram
      )

    override def reads(json: JsValue): JsResult[PgStatsRow] = {
      JsResult.fromTry(
        Try(
          PgStatsRow(
            schemaname = json.\("schemaname").as[String],
            tablename = json.\("tablename").as[String],
            attname = json.\("attname").as[String],
            inherited = json.\("inherited").as[Boolean],
            nullFrac = json.\("null_frac").as[Float],
            avgWidth = json.\("avg_width").as[Int],
            nDistinct = json.\("n_distinct").as[Float],
            mostCommonVals = json.\("most_common_vals").toOption.map(_.as[/* anyarray */ String]),
            mostCommonFreqs = json.\("most_common_freqs").toOption.map(_.as[Array[Float]]),
            histogramBounds = json.\("histogram_bounds").toOption.map(_.as[/* anyarray */ String]),
            correlation = json.\("correlation").toOption.map(_.as[Float]),
            mostCommonElems = json.\("most_common_elems").toOption.map(_.as[/* anyarray */ String]),
            mostCommonElemFreqs = json.\("most_common_elem_freqs").toOption.map(_.as[Array[Float]]),
            elemCountHistogram = json.\("elem_count_histogram").toOption.map(_.as[Array[Float]])
          )
        )
      )
    }
  }
}
