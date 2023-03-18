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

case class PgCastRow(
  oid: PgCastId,
  castsource: Long,
  casttarget: Long,
  castfunc: Long,
  castcontext: String,
  castmethod: String
)

object PgCastRow {
  implicit val rowParser: RowParser[PgCastRow] = { row =>
    Success(
      PgCastRow(
        oid = row[PgCastId]("oid"),
        castsource = row[Long]("castsource"),
        casttarget = row[Long]("casttarget"),
        castfunc = row[Long]("castfunc"),
        castcontext = row[String]("castcontext"),
        castmethod = row[String]("castmethod")
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
            castsource = json.\("castsource").as[Long],
            casttarget = json.\("casttarget").as[Long],
            castfunc = json.\("castfunc").as[Long],
            castcontext = json.\("castcontext").as[String],
            castmethod = json.\("castmethod").as[String]
          )
        )
      )
    }
  }
}
