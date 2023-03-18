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

case class PgInheritsRow(
  inhrelid: Long,
  inhparent: Long,
  inhseqno: Int,
  inhdetachpending: Boolean
){
  val inhrelidAndInhseqno: PgInheritsId = PgInheritsId(inhrelid, inhseqno)
}

object PgInheritsRow {
  implicit val rowParser: RowParser[PgInheritsRow] = { row =>
    Success(
      PgInheritsRow(
        inhrelid = row[Long]("inhrelid"),
        inhparent = row[Long]("inhparent"),
        inhseqno = row[Int]("inhseqno"),
        inhdetachpending = row[Boolean]("inhdetachpending")
      )
    )
  }

  implicit val oFormat: OFormat[PgInheritsRow] = new OFormat[PgInheritsRow]{
    override def writes(o: PgInheritsRow): JsObject =
      Json.obj(
        "inhrelid" -> o.inhrelid,
      "inhparent" -> o.inhparent,
      "inhseqno" -> o.inhseqno,
      "inhdetachpending" -> o.inhdetachpending
      )

    override def reads(json: JsValue): JsResult[PgInheritsRow] = {
      JsResult.fromTry(
        Try(
          PgInheritsRow(
            inhrelid = json.\("inhrelid").as[Long],
            inhparent = json.\("inhparent").as[Long],
            inhseqno = json.\("inhseqno").as[Int],
            inhdetachpending = json.\("inhdetachpending").as[Boolean]
          )
        )
      )
    }
  }
}
