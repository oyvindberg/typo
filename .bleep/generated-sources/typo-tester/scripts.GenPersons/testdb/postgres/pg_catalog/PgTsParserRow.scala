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

case class PgTsParserRow(
  oid: PgTsParserId,
  prsname: String,
  prsnamespace: Long,
  prsstart: String,
  prstoken: String,
  prsend: String,
  prsheadline: String,
  prslextype: String
)

object PgTsParserRow {
  implicit val rowParser: RowParser[PgTsParserRow] = { row =>
    Success(
      PgTsParserRow(
        oid = row[PgTsParserId]("oid"),
        prsname = row[String]("prsname"),
        prsnamespace = row[Long]("prsnamespace"),
        prsstart = row[String]("prsstart"),
        prstoken = row[String]("prstoken"),
        prsend = row[String]("prsend"),
        prsheadline = row[String]("prsheadline"),
        prslextype = row[String]("prslextype")
      )
    )
  }

  implicit val oFormat: OFormat[PgTsParserRow] = new OFormat[PgTsParserRow]{
    override def writes(o: PgTsParserRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "prsname" -> o.prsname,
      "prsnamespace" -> o.prsnamespace,
      "prsstart" -> o.prsstart,
      "prstoken" -> o.prstoken,
      "prsend" -> o.prsend,
      "prsheadline" -> o.prsheadline,
      "prslextype" -> o.prslextype
      )

    override def reads(json: JsValue): JsResult[PgTsParserRow] = {
      JsResult.fromTry(
        Try(
          PgTsParserRow(
            oid = json.\("oid").as[PgTsParserId],
            prsname = json.\("prsname").as[String],
            prsnamespace = json.\("prsnamespace").as[Long],
            prsstart = json.\("prsstart").as[String],
            prstoken = json.\("prstoken").as[String],
            prsend = json.\("prsend").as[String],
            prsheadline = json.\("prsheadline").as[String],
            prslextype = json.\("prslextype").as[String]
          )
        )
      )
    }
  }
}
