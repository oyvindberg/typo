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

case class PgAttrdefRow(
  oid: PgAttrdefId,
  adrelid: Long,
  adnum: Short,
  adbin: String
)

object PgAttrdefRow {
  implicit val rowParser: RowParser[PgAttrdefRow] = { row =>
    Success(
      PgAttrdefRow(
        oid = row[PgAttrdefId]("oid"),
        adrelid = row[Long]("adrelid"),
        adnum = row[Short]("adnum"),
        adbin = row[String]("adbin")
      )
    )
  }

  implicit val oFormat: OFormat[PgAttrdefRow] = new OFormat[PgAttrdefRow]{
    override def writes(o: PgAttrdefRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "adrelid" -> o.adrelid,
      "adnum" -> o.adnum,
      "adbin" -> o.adbin
      )

    override def reads(json: JsValue): JsResult[PgAttrdefRow] = {
      JsResult.fromTry(
        Try(
          PgAttrdefRow(
            oid = json.\("oid").as[PgAttrdefId],
            adrelid = json.\("adrelid").as[Long],
            adnum = json.\("adnum").as[Short],
            adbin = json.\("adbin").as[String]
          )
        )
      )
    }
  }
}
