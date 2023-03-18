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

case class PgForeignDataWrapperRow(
  oid: Long,
  fdwname: String,
  fdwowner: Long,
  fdwhandler: Long,
  fdwvalidator: Long,
  fdwacl: Option[Array[String]],
  fdwoptions: Option[Array[String]]
)

object PgForeignDataWrapperRow {
  implicit val rowParser: RowParser[PgForeignDataWrapperRow] = { row =>
    Success(
      PgForeignDataWrapperRow(
        oid = row[Long]("oid"),
        fdwname = row[String]("fdwname"),
        fdwowner = row[Long]("fdwowner"),
        fdwhandler = row[Long]("fdwhandler"),
        fdwvalidator = row[Long]("fdwvalidator"),
        fdwacl = row[Option[Array[String]]]("fdwacl"),
        fdwoptions = row[Option[Array[String]]]("fdwoptions")
      )
    )
  }

  implicit val oFormat: OFormat[PgForeignDataWrapperRow] = new OFormat[PgForeignDataWrapperRow]{
    override def writes(o: PgForeignDataWrapperRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "fdwname" -> o.fdwname,
      "fdwowner" -> o.fdwowner,
      "fdwhandler" -> o.fdwhandler,
      "fdwvalidator" -> o.fdwvalidator,
      "fdwacl" -> o.fdwacl,
      "fdwoptions" -> o.fdwoptions
      )

    override def reads(json: JsValue): JsResult[PgForeignDataWrapperRow] = {
      JsResult.fromTry(
        Try(
          PgForeignDataWrapperRow(
            oid = json.\("oid").as[Long],
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
