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

case class PgNamespaceRow(
  oid: Long,
  nspname: String,
  nspowner: Long,
  nspacl: Option[Array[String]]
)

object PgNamespaceRow {
  implicit val rowParser: RowParser[PgNamespaceRow] = { row =>
    Success(
      PgNamespaceRow(
        oid = row[Long]("oid"),
        nspname = row[String]("nspname"),
        nspowner = row[Long]("nspowner"),
        nspacl = row[Option[Array[String]]]("nspacl")
      )
    )
  }

  implicit val oFormat: OFormat[PgNamespaceRow] = new OFormat[PgNamespaceRow]{
    override def writes(o: PgNamespaceRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "nspname" -> o.nspname,
      "nspowner" -> o.nspowner,
      "nspacl" -> o.nspacl
      )

    override def reads(json: JsValue): JsResult[PgNamespaceRow] = {
      JsResult.fromTry(
        Try(
          PgNamespaceRow(
            oid = json.\("oid").as[Long],
            nspname = json.\("nspname").as[String],
            nspowner = json.\("nspowner").as[Long],
            nspacl = json.\("nspacl").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
