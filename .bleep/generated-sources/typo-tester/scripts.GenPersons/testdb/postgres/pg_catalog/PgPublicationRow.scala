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

case class PgPublicationRow(
  oid: Long,
  pubname: String,
  pubowner: Long,
  puballtables: Boolean,
  pubinsert: Boolean,
  pubupdate: Boolean,
  pubdelete: Boolean,
  pubtruncate: Boolean,
  pubviaroot: Boolean
)

object PgPublicationRow {
  implicit val rowParser: RowParser[PgPublicationRow] = { row =>
    Success(
      PgPublicationRow(
        oid = row[Long]("oid"),
        pubname = row[String]("pubname"),
        pubowner = row[Long]("pubowner"),
        puballtables = row[Boolean]("puballtables"),
        pubinsert = row[Boolean]("pubinsert"),
        pubupdate = row[Boolean]("pubupdate"),
        pubdelete = row[Boolean]("pubdelete"),
        pubtruncate = row[Boolean]("pubtruncate"),
        pubviaroot = row[Boolean]("pubviaroot")
      )
    )
  }

  implicit val oFormat: OFormat[PgPublicationRow] = new OFormat[PgPublicationRow]{
    override def writes(o: PgPublicationRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "pubname" -> o.pubname,
      "pubowner" -> o.pubowner,
      "puballtables" -> o.puballtables,
      "pubinsert" -> o.pubinsert,
      "pubupdate" -> o.pubupdate,
      "pubdelete" -> o.pubdelete,
      "pubtruncate" -> o.pubtruncate,
      "pubviaroot" -> o.pubviaroot
      )

    override def reads(json: JsValue): JsResult[PgPublicationRow] = {
      JsResult.fromTry(
        Try(
          PgPublicationRow(
            oid = json.\("oid").as[Long],
            pubname = json.\("pubname").as[String],
            pubowner = json.\("pubowner").as[Long],
            puballtables = json.\("puballtables").as[Boolean],
            pubinsert = json.\("pubinsert").as[Boolean],
            pubupdate = json.\("pubupdate").as[Boolean],
            pubdelete = json.\("pubdelete").as[Boolean],
            pubtruncate = json.\("pubtruncate").as[Boolean],
            pubviaroot = json.\("pubviaroot").as[Boolean]
          )
        )
      )
    }
  }
}
