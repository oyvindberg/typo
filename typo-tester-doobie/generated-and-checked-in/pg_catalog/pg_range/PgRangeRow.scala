/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_range

import adventureworks.TypoRegproc
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet

case class PgRangeRow(
  rngtypid: PgRangeId,
  rngsubtype: /* oid */ Long,
  rngmultitypid: /* oid */ Long,
  rngcollation: /* oid */ Long,
  rngsubopc: /* oid */ Long,
  rngcanonical: TypoRegproc,
  rngsubdiff: TypoRegproc
)

object PgRangeRow {
  implicit val decoder: Decoder[PgRangeRow] =
    (c: HCursor) =>
      for {
        rngtypid <- c.downField("rngtypid").as[PgRangeId]
        rngsubtype <- c.downField("rngsubtype").as[/* oid */ Long]
        rngmultitypid <- c.downField("rngmultitypid").as[/* oid */ Long]
        rngcollation <- c.downField("rngcollation").as[/* oid */ Long]
        rngsubopc <- c.downField("rngsubopc").as[/* oid */ Long]
        rngcanonical <- c.downField("rngcanonical").as[TypoRegproc]
        rngsubdiff <- c.downField("rngsubdiff").as[TypoRegproc]
      } yield PgRangeRow(rngtypid, rngsubtype, rngmultitypid, rngcollation, rngsubopc, rngcanonical, rngsubdiff)
  implicit val encoder: Encoder[PgRangeRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "rngtypid" := row.rngtypid,
        "rngsubtype" := row.rngsubtype,
        "rngmultitypid" := row.rngmultitypid,
        "rngcollation" := row.rngcollation,
        "rngsubopc" := row.rngsubopc,
        "rngcanonical" := row.rngcanonical,
        "rngsubdiff" := row.rngsubdiff
      )}
  implicit val read: Read[PgRangeRow] =
    new Read[PgRangeRow](
      gets = List(
        (Get[PgRangeId], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[/* oid */ Long], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls),
        (Get[TypoRegproc], Nullability.NoNulls)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => PgRangeRow(
        rngtypid = Get[PgRangeId].unsafeGetNonNullable(rs, i + 0),
        rngsubtype = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 1),
        rngmultitypid = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 2),
        rngcollation = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 3),
        rngsubopc = Get[/* oid */ Long].unsafeGetNonNullable(rs, i + 4),
        rngcanonical = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 5),
        rngsubdiff = Get[TypoRegproc].unsafeGetNonNullable(rs, i + 6)
      )
    )
  

}