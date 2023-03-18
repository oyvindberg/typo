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

case class PgAmRow(
  oid: PgAmId,
  amname: String,
  amhandler: String,
  amtype: String
)

object PgAmRow {
  implicit val rowParser: RowParser[PgAmRow] = { row =>
    Success(
      PgAmRow(
        oid = row[PgAmId]("oid"),
        amname = row[String]("amname"),
        amhandler = row[String]("amhandler"),
        amtype = row[String]("amtype")
      )
    )
  }

  implicit val oFormat: OFormat[PgAmRow] = new OFormat[PgAmRow]{
    override def writes(o: PgAmRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "amname" -> o.amname,
      "amhandler" -> o.amhandler,
      "amtype" -> o.amtype
      )

    override def reads(json: JsValue): JsResult[PgAmRow] = {
      JsResult.fromTry(
        Try(
          PgAmRow(
            oid = json.\("oid").as[PgAmId],
            amname = json.\("amname").as[String],
            amhandler = json.\("amhandler").as[String],
            amtype = json.\("amtype").as[String]
          )
        )
      )
    }
  }
}
