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

case class PgReplicationOriginRow(
  roident: Long,
  roname: String
)

object PgReplicationOriginRow {
  implicit val rowParser: RowParser[PgReplicationOriginRow] = { row =>
    Success(
      PgReplicationOriginRow(
        roident = row[Long]("roident"),
        roname = row[String]("roname")
      )
    )
  }

  implicit val oFormat: OFormat[PgReplicationOriginRow] = new OFormat[PgReplicationOriginRow]{
    override def writes(o: PgReplicationOriginRow): JsObject =
      Json.obj(
        "roident" -> o.roident,
      "roname" -> o.roname
      )

    override def reads(json: JsValue): JsResult[PgReplicationOriginRow] = {
      JsResult.fromTry(
        Try(
          PgReplicationOriginRow(
            roident = json.\("roident").as[Long],
            roname = json.\("roname").as[String]
          )
        )
      )
    }
  }
}
