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

case class PgInheritsId(inhrelid: Long, inhseqno: Int)
object PgInheritsId {
  implicit val ordering: Ordering[PgInheritsId] = Ordering.by(x => (x.inhrelid, x.inhseqno))
  implicit val oFormat: OFormat[PgInheritsId] = new OFormat[PgInheritsId]{
    override def writes(o: PgInheritsId): JsObject =
      Json.obj(
        "inhrelid" -> o.inhrelid,
      "inhseqno" -> o.inhseqno
      )

    override def reads(json: JsValue): JsResult[PgInheritsId] = {
      JsResult.fromTry(
        Try(
          PgInheritsId(
            inhrelid = json.\("inhrelid").as[Long],
            inhseqno = json.\("inhseqno").as[Int]
          )
        )
      )
    }
  }
  implicit val rowParser: RowParser[PgInheritsId] = { row =>
    Success(
      PgInheritsId(
        inhrelid = row[Long]("inhrelid"),
        inhseqno = row[Int]("inhseqno")
      )
    )
  }

}
