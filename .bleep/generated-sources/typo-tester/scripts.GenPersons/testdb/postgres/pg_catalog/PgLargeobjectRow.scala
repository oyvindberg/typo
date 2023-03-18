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

case class PgLargeobjectRow(
  loid: Long,
  pageno: Int,
  data: String
)

object PgLargeobjectRow {
  implicit val rowParser: RowParser[PgLargeobjectRow] = { row =>
    Success(
      PgLargeobjectRow(
        loid = row[Long]("loid"),
        pageno = row[Int]("pageno"),
        data = row[String]("data")
      )
    )
  }

  implicit val oFormat: OFormat[PgLargeobjectRow] = new OFormat[PgLargeobjectRow]{
    override def writes(o: PgLargeobjectRow): JsObject =
      Json.obj(
        "loid" -> o.loid,
      "pageno" -> o.pageno,
      "data" -> o.data
      )

    override def reads(json: JsValue): JsResult[PgLargeobjectRow] = {
      JsResult.fromTry(
        Try(
          PgLargeobjectRow(
            loid = json.\("loid").as[Long],
            pageno = json.\("pageno").as[Int],
            data = json.\("data").as[String]
          )
        )
      )
    }
  }
}
