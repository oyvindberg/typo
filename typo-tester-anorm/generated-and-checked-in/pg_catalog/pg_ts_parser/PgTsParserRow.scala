/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_ts_parser

import adventureworks.TypoRegproc
import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class PgTsParserRow(
  oid: PgTsParserId,
  prsname: String,
  prsnamespace: /* oid */ Long,
  prsstart: TypoRegproc,
  prstoken: TypoRegproc,
  prsend: TypoRegproc,
  prsheadline: TypoRegproc,
  prslextype: TypoRegproc
)

object PgTsParserRow {
  implicit lazy val reads: Reads[PgTsParserRow] = Reads[PgTsParserRow](json => JsResult.fromTry(
      Try(
        PgTsParserRow(
          oid = json.\("oid").as(PgTsParserId.reads),
          prsname = json.\("prsname").as(Reads.StringReads),
          prsnamespace = json.\("prsnamespace").as(Reads.LongReads),
          prsstart = json.\("prsstart").as(TypoRegproc.reads),
          prstoken = json.\("prstoken").as(TypoRegproc.reads),
          prsend = json.\("prsend").as(TypoRegproc.reads),
          prsheadline = json.\("prsheadline").as(TypoRegproc.reads),
          prslextype = json.\("prslextype").as(TypoRegproc.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PgTsParserRow] = RowParser[PgTsParserRow] { row =>
    Success(
      PgTsParserRow(
        oid = row(idx + 0)(PgTsParserId.column),
        prsname = row(idx + 1)(Column.columnToString),
        prsnamespace = row(idx + 2)(Column.columnToLong),
        prsstart = row(idx + 3)(TypoRegproc.column),
        prstoken = row(idx + 4)(TypoRegproc.column),
        prsend = row(idx + 5)(TypoRegproc.column),
        prsheadline = row(idx + 6)(TypoRegproc.column),
        prslextype = row(idx + 7)(TypoRegproc.column)
      )
    )
  }
  implicit lazy val writes: OWrites[PgTsParserRow] = OWrites[PgTsParserRow](o =>
    new JsObject(ListMap[String, JsValue](
      "oid" -> PgTsParserId.writes.writes(o.oid),
      "prsname" -> Writes.StringWrites.writes(o.prsname),
      "prsnamespace" -> Writes.LongWrites.writes(o.prsnamespace),
      "prsstart" -> TypoRegproc.writes.writes(o.prsstart),
      "prstoken" -> TypoRegproc.writes.writes(o.prstoken),
      "prsend" -> TypoRegproc.writes.writes(o.prsend),
      "prsheadline" -> TypoRegproc.writes.writes(o.prsheadline),
      "prslextype" -> TypoRegproc.writes.writes(o.prslextype)
    ))
  )
}