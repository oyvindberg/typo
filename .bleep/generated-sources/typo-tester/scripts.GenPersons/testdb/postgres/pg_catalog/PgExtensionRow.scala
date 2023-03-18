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

case class PgExtensionRow(
  oid: Long,
  extname: String,
  extowner: Long,
  extnamespace: Long,
  extrelocatable: Boolean,
  extversion: String,
  extconfig: Option[Array[Long]],
  extcondition: Option[Array[String]]
)

object PgExtensionRow {
  implicit val rowParser: RowParser[PgExtensionRow] = { row =>
    Success(
      PgExtensionRow(
        oid = row[Long]("oid"),
        extname = row[String]("extname"),
        extowner = row[Long]("extowner"),
        extnamespace = row[Long]("extnamespace"),
        extrelocatable = row[Boolean]("extrelocatable"),
        extversion = row[String]("extversion"),
        extconfig = row[Option[Array[Long]]]("extconfig"),
        extcondition = row[Option[Array[String]]]("extcondition")
      )
    )
  }

  implicit val oFormat: OFormat[PgExtensionRow] = new OFormat[PgExtensionRow]{
    override def writes(o: PgExtensionRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "extname" -> o.extname,
      "extowner" -> o.extowner,
      "extnamespace" -> o.extnamespace,
      "extrelocatable" -> o.extrelocatable,
      "extversion" -> o.extversion,
      "extconfig" -> o.extconfig,
      "extcondition" -> o.extcondition
      )

    override def reads(json: JsValue): JsResult[PgExtensionRow] = {
      JsResult.fromTry(
        Try(
          PgExtensionRow(
            oid = json.\("oid").as[Long],
            extname = json.\("extname").as[String],
            extowner = json.\("extowner").as[Long],
            extnamespace = json.\("extnamespace").as[Long],
            extrelocatable = json.\("extrelocatable").as[Boolean],
            extversion = json.\("extversion").as[String],
            extconfig = json.\("extconfig").toOption.map(_.as[Array[Long]]),
            extcondition = json.\("extcondition").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
