package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatsExtRow(
  schemaname: String,
  tablename: String,
  statisticsSchemaname: String,
  statisticsName: String,
  statisticsOwner: /* unknown nullability */ Option[String],
  attnames: /* typo doesn't know how to translate: columnType: Array, columnTypeName: _name, columnClassName: java.sql.Array */ Any,
  exprs: /* unknown nullability */ Option[Array[String]],
  kinds: Array[String],
  nDistinct: Option[/* pg_ndistinct */ String],
  dependencies: Option[/* pg_dependencies */ String],
  mostCommonVals: /* unknown nullability */ Option[Array[String]],
  mostCommonValNulls: /* typo doesn't know how to translate: columnType: Array, columnTypeName: _bool, columnClassName: java.sql.Array */ Any,
  mostCommonFreqs: /* unknown nullability */ Option[Array[Double]],
  mostCommonBaseFreqs: /* unknown nullability */ Option[Array[Double]]
)

object PgStatsExtRow {
  implicit val rowParser: RowParser[PgStatsExtRow] = { row =>
    Success(
      PgStatsExtRow(
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename"),
        statisticsSchemaname = row[String]("statistics_schemaname"),
        statisticsName = row[String]("statistics_name"),
        statisticsOwner = row[/* unknown nullability */ Option[String]]("statistics_owner"),
        attnames = row[/* typo doesn't know how to translate: columnType: Array, columnTypeName: _name, columnClassName: java.sql.Array */ Any]("attnames"),
        exprs = row[/* unknown nullability */ Option[Array[String]]]("exprs"),
        kinds = row[Array[String]]("kinds"),
        nDistinct = row[Option[/* pg_ndistinct */ String]]("n_distinct"),
        dependencies = row[Option[/* pg_dependencies */ String]]("dependencies"),
        mostCommonVals = row[/* unknown nullability */ Option[Array[String]]]("most_common_vals"),
        mostCommonValNulls = row[/* typo doesn't know how to translate: columnType: Array, columnTypeName: _bool, columnClassName: java.sql.Array */ Any]("most_common_val_nulls"),
        mostCommonFreqs = row[/* unknown nullability */ Option[Array[Double]]]("most_common_freqs"),
        mostCommonBaseFreqs = row[/* unknown nullability */ Option[Array[Double]]]("most_common_base_freqs")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatsExtRow] = Json.format
}
