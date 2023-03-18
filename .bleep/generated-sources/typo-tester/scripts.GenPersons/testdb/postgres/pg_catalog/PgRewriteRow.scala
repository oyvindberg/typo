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

case class PgRewriteRow(
  oid: Long,
  rulename: String,
  evClass: Long,
  evType: String,
  evEnabled: String,
  isInstead: Boolean,
  evQual: String,
  evAction: String
)

object PgRewriteRow {
  implicit val rowParser: RowParser[PgRewriteRow] = { row =>
    Success(
      PgRewriteRow(
        oid = row[Long]("oid"),
        rulename = row[String]("rulename"),
        evClass = row[Long]("ev_class"),
        evType = row[String]("ev_type"),
        evEnabled = row[String]("ev_enabled"),
        isInstead = row[Boolean]("is_instead"),
        evQual = row[String]("ev_qual"),
        evAction = row[String]("ev_action")
      )
    )
  }

  implicit val oFormat: OFormat[PgRewriteRow] = new OFormat[PgRewriteRow]{
    override def writes(o: PgRewriteRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "rulename" -> o.rulename,
      "ev_class" -> o.evClass,
      "ev_type" -> o.evType,
      "ev_enabled" -> o.evEnabled,
      "is_instead" -> o.isInstead,
      "ev_qual" -> o.evQual,
      "ev_action" -> o.evAction
      )

    override def reads(json: JsValue): JsResult[PgRewriteRow] = {
      JsResult.fromTry(
        Try(
          PgRewriteRow(
            oid = json.\("oid").as[Long],
            rulename = json.\("rulename").as[String],
            evClass = json.\("ev_class").as[Long],
            evType = json.\("ev_type").as[String],
            evEnabled = json.\("ev_enabled").as[String],
            isInstead = json.\("is_instead").as[Boolean],
            evQual = json.\("ev_qual").as[String],
            evAction = json.\("ev_action").as[String]
          )
        )
      )
    }
  }
}
