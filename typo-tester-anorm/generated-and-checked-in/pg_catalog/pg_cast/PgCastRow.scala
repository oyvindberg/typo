/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_cast

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgCastRow(
  oid: PgCastId,
  castsource: /* oid */ Long,
  casttarget: /* oid */ Long,
  castfunc: /* oid */ Long,
  castcontext: String,
  castmethod: String
)

object PgCastRow {
  def rowParser(idx: Int): RowParser[PgCastRow] =
    RowParser[PgCastRow] { row =>
      Success(
        PgCastRow(
          oid = row[PgCastId](idx + 0),
          castsource = row[/* oid */ Long](idx + 1),
          casttarget = row[/* oid */ Long](idx + 2),
          castfunc = row[/* oid */ Long](idx + 3),
          castcontext = row[String](idx + 4),
          castmethod = row[String](idx + 5)
        )
      )
    }
  implicit val oFormat: OFormat[PgCastRow] = new OFormat[PgCastRow]{
    override def writes(o: PgCastRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
        "castsource" -> o.castsource,
        "casttarget" -> o.casttarget,
        "castfunc" -> o.castfunc,
        "castcontext" -> o.castcontext,
        "castmethod" -> o.castmethod
      )
  
    override def reads(json: JsValue): JsResult[PgCastRow] = {
      JsResult.fromTry(
        Try(
          PgCastRow(
            oid = json.\("oid").as[PgCastId],
            castsource = json.\("castsource").as[/* oid */ Long],
            casttarget = json.\("casttarget").as[/* oid */ Long],
            castfunc = json.\("castfunc").as[/* oid */ Long],
            castcontext = json.\("castcontext").as[String],
            castmethod = json.\("castmethod").as[String]
          )
        )
      )
    }
  }
}