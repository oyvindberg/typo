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

case class PgCollationRow(
  oid: Long,
  collname: String,
  collnamespace: Long,
  collowner: Long,
  collprovider: String,
  collisdeterministic: Boolean,
  collencoding: Int,
  collcollate: String,
  collctype: String,
  collversion: Option[String]
)

object PgCollationRow {
  implicit val rowParser: RowParser[PgCollationRow] = { row =>
    Success(
      PgCollationRow(
        oid = row[Long]("oid"),
        collname = row[String]("collname"),
        collnamespace = row[Long]("collnamespace"),
        collowner = row[Long]("collowner"),
        collprovider = row[String]("collprovider"),
        collisdeterministic = row[Boolean]("collisdeterministic"),
        collencoding = row[Int]("collencoding"),
        collcollate = row[String]("collcollate"),
        collctype = row[String]("collctype"),
        collversion = row[Option[String]]("collversion")
      )
    )
  }

  implicit val oFormat: OFormat[PgCollationRow] = new OFormat[PgCollationRow]{
    override def writes(o: PgCollationRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "collname" -> o.collname,
      "collnamespace" -> o.collnamespace,
      "collowner" -> o.collowner,
      "collprovider" -> o.collprovider,
      "collisdeterministic" -> o.collisdeterministic,
      "collencoding" -> o.collencoding,
      "collcollate" -> o.collcollate,
      "collctype" -> o.collctype,
      "collversion" -> o.collversion
      )

    override def reads(json: JsValue): JsResult[PgCollationRow] = {
      JsResult.fromTry(
        Try(
          PgCollationRow(
            oid = json.\("oid").as[Long],
            collname = json.\("collname").as[String],
            collnamespace = json.\("collnamespace").as[Long],
            collowner = json.\("collowner").as[Long],
            collprovider = json.\("collprovider").as[String],
            collisdeterministic = json.\("collisdeterministic").as[Boolean],
            collencoding = json.\("collencoding").as[Int],
            collcollate = json.\("collcollate").as[String],
            collctype = json.\("collctype").as[String],
            collversion = json.\("collversion").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
