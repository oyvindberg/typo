package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgForeignDataWrapperRowUnsaved(
  fdwname: String,
  fdwowner: Long,
  fdwhandler: Long,
  fdwvalidator: Long,
  fdwacl: Option[Array[String]],
  fdwoptions: Option[Array[String]]
)
object PgForeignDataWrapperRowUnsaved {
  implicit val oFormat: OFormat[PgForeignDataWrapperRowUnsaved] = new OFormat[PgForeignDataWrapperRowUnsaved]{
    override def writes(o: PgForeignDataWrapperRowUnsaved): JsObject =
      Json.obj(
        "fdwname" -> o.fdwname,
      "fdwowner" -> o.fdwowner,
      "fdwhandler" -> o.fdwhandler,
      "fdwvalidator" -> o.fdwvalidator,
      "fdwacl" -> o.fdwacl,
      "fdwoptions" -> o.fdwoptions
      )

    override def reads(json: JsValue): JsResult[PgForeignDataWrapperRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgForeignDataWrapperRowUnsaved(
            fdwname = json.\("fdwname").as[String],
            fdwowner = json.\("fdwowner").as[Long],
            fdwhandler = json.\("fdwhandler").as[Long],
            fdwvalidator = json.\("fdwvalidator").as[Long],
            fdwacl = json.\("fdwacl").toOption.map(_.as[Array[String]]),
            fdwoptions = json.\("fdwoptions").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
