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

case class PgLargeobjectId(loid: Long, pageno: Int)
object PgLargeobjectId {
  implicit val ordering: Ordering[PgLargeobjectId] = Ordering.by(x => (x.loid, x.pageno))
  implicit val oFormat: OFormat[PgLargeobjectId] = new OFormat[PgLargeobjectId]{
    override def writes(o: PgLargeobjectId): JsObject =
      Json.obj(
        "loid" -> o.loid,
      "pageno" -> o.pageno
      )

    override def reads(json: JsValue): JsResult[PgLargeobjectId] = {
      JsResult.fromTry(
        Try(
          PgLargeobjectId(
            loid = json.\("loid").as[Long],
            pageno = json.\("pageno").as[Int]
          )
        )
      )
    }
  }
  implicit val rowParser: RowParser[PgLargeobjectId] = { row =>
    Success(
      PgLargeobjectId(
        loid = row[Long]("loid"),
        pageno = row[Int]("pageno")
      )
    )
  }

}
