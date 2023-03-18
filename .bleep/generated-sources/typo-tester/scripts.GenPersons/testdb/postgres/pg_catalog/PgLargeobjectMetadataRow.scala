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

case class PgLargeobjectMetadataRow(
  oid: Long,
  lomowner: Long,
  lomacl: Option[Array[String]]
)

object PgLargeobjectMetadataRow {
  implicit val rowParser: RowParser[PgLargeobjectMetadataRow] = { row =>
    Success(
      PgLargeobjectMetadataRow(
        oid = row[Long]("oid"),
        lomowner = row[Long]("lomowner"),
        lomacl = row[Option[Array[String]]]("lomacl")
      )
    )
  }

  implicit val oFormat: OFormat[PgLargeobjectMetadataRow] = new OFormat[PgLargeobjectMetadataRow]{
    override def writes(o: PgLargeobjectMetadataRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "lomowner" -> o.lomowner,
      "lomacl" -> o.lomacl
      )

    override def reads(json: JsValue): JsResult[PgLargeobjectMetadataRow] = {
      JsResult.fromTry(
        Try(
          PgLargeobjectMetadataRow(
            oid = json.\("oid").as[Long],
            lomowner = json.\("lomowner").as[Long],
            lomacl = json.\("lomacl").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
