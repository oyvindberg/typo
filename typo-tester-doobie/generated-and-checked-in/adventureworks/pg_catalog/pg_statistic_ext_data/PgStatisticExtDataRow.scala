/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_statistic_ext_data

import adventureworks.customtypes.TypoUnknownPgDependencies
import adventureworks.customtypes.TypoUnknownPgMcvList
import adventureworks.customtypes.TypoUnknownPgNdistinct
import adventureworks.customtypes.TypoUnknownPgStatistic
import doobie.enumerated.Nullability
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class PgStatisticExtDataRow(
  stxoid: PgStatisticExtDataId,
  stxdndistinct: Option[TypoUnknownPgNdistinct],
  stxddependencies: Option[TypoUnknownPgDependencies],
  stxdmcv: Option[TypoUnknownPgMcvList],
  stxdexpr: Option[Array[TypoUnknownPgStatistic]]
)

object PgStatisticExtDataRow {
  implicit lazy val decoder: Decoder[PgStatisticExtDataRow] = Decoder.forProduct5[PgStatisticExtDataRow, PgStatisticExtDataId, Option[TypoUnknownPgNdistinct], Option[TypoUnknownPgDependencies], Option[TypoUnknownPgMcvList], Option[Array[TypoUnknownPgStatistic]]]("stxoid", "stxdndistinct", "stxddependencies", "stxdmcv", "stxdexpr")(PgStatisticExtDataRow.apply)(PgStatisticExtDataId.decoder, Decoder.decodeOption(TypoUnknownPgNdistinct.decoder), Decoder.decodeOption(TypoUnknownPgDependencies.decoder), Decoder.decodeOption(TypoUnknownPgMcvList.decoder), Decoder.decodeOption(Decoder.decodeArray[TypoUnknownPgStatistic](TypoUnknownPgStatistic.decoder, implicitly)))
  implicit lazy val encoder: Encoder[PgStatisticExtDataRow] = Encoder.forProduct5[PgStatisticExtDataRow, PgStatisticExtDataId, Option[TypoUnknownPgNdistinct], Option[TypoUnknownPgDependencies], Option[TypoUnknownPgMcvList], Option[Array[TypoUnknownPgStatistic]]]("stxoid", "stxdndistinct", "stxddependencies", "stxdmcv", "stxdexpr")(x => (x.stxoid, x.stxdndistinct, x.stxddependencies, x.stxdmcv, x.stxdexpr))(PgStatisticExtDataId.encoder, Encoder.encodeOption(TypoUnknownPgNdistinct.encoder), Encoder.encodeOption(TypoUnknownPgDependencies.encoder), Encoder.encodeOption(TypoUnknownPgMcvList.encoder), Encoder.encodeOption(Encoder.encodeIterable[TypoUnknownPgStatistic, Array](TypoUnknownPgStatistic.encoder, implicitly)))
  implicit lazy val read: Read[PgStatisticExtDataRow] = new Read[PgStatisticExtDataRow](
    gets = List(
      (PgStatisticExtDataId.get, Nullability.NoNulls),
      (TypoUnknownPgNdistinct.get, Nullability.Nullable),
      (TypoUnknownPgDependencies.get, Nullability.Nullable),
      (TypoUnknownPgMcvList.get, Nullability.Nullable),
      (TypoUnknownPgStatistic.arrayGet, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PgStatisticExtDataRow(
      stxoid = PgStatisticExtDataId.get.unsafeGetNonNullable(rs, i + 0),
      stxdndistinct = TypoUnknownPgNdistinct.get.unsafeGetNullable(rs, i + 1),
      stxddependencies = TypoUnknownPgDependencies.get.unsafeGetNullable(rs, i + 2),
      stxdmcv = TypoUnknownPgMcvList.get.unsafeGetNullable(rs, i + 3),
      stxdexpr = TypoUnknownPgStatistic.arrayGet.unsafeGetNullable(rs, i + 4)
    )
  )
}