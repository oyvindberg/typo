/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_rules

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgRulesViewRow(
  schemaname: Option[String],
  tablename: Option[String],
  rulename: Option[String],
  definition: Option[String]
)

object PgRulesViewRow {
  def rowParser(idx: Int): RowParser[PgRulesViewRow] =
    RowParser[PgRulesViewRow] { row =>
      Success(
        PgRulesViewRow(
          schemaname = row[Option[String]](idx + 0),
          tablename = row[Option[String]](idx + 1),
          rulename = row[Option[String]](idx + 2),
          definition = row[Option[String]](idx + 3)
        )
      )
    }
  implicit val oFormat: OFormat[PgRulesViewRow] = new OFormat[PgRulesViewRow]{
    override def writes(o: PgRulesViewRow): JsObject =
      Json.obj(
        "schemaname" -> o.schemaname,
        "tablename" -> o.tablename,
        "rulename" -> o.rulename,
        "definition" -> o.definition
      )
  
    override def reads(json: JsValue): JsResult[PgRulesViewRow] = {
      JsResult.fromTry(
        Try(
          PgRulesViewRow(
            schemaname = json.\("schemaname").toOption.map(_.as[String]),
            tablename = json.\("tablename").toOption.map(_.as[String]),
            rulename = json.\("rulename").toOption.map(_.as[String]),
            definition = json.\("definition").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}