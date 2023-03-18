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

case class PgForeignServerRow(
  oid: Long,
  srvname: String,
  srvowner: Long,
  srvfdw: Long,
  srvtype: Option[String],
  srvversion: Option[String],
  srvacl: Option[Array[String]],
  srvoptions: Option[Array[String]]
)

object PgForeignServerRow {
  implicit val rowParser: RowParser[PgForeignServerRow] = { row =>
    Success(
      PgForeignServerRow(
        oid = row[Long]("oid"),
        srvname = row[String]("srvname"),
        srvowner = row[Long]("srvowner"),
        srvfdw = row[Long]("srvfdw"),
        srvtype = row[Option[String]]("srvtype"),
        srvversion = row[Option[String]]("srvversion"),
        srvacl = row[Option[Array[String]]]("srvacl"),
        srvoptions = row[Option[Array[String]]]("srvoptions")
      )
    )
  }

  implicit val oFormat: OFormat[PgForeignServerRow] = new OFormat[PgForeignServerRow]{
    override def writes(o: PgForeignServerRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "srvname" -> o.srvname,
      "srvowner" -> o.srvowner,
      "srvfdw" -> o.srvfdw,
      "srvtype" -> o.srvtype,
      "srvversion" -> o.srvversion,
      "srvacl" -> o.srvacl,
      "srvoptions" -> o.srvoptions
      )

    override def reads(json: JsValue): JsResult[PgForeignServerRow] = {
      JsResult.fromTry(
        Try(
          PgForeignServerRow(
            oid = json.\("oid").as[Long],
            srvname = json.\("srvname").as[String],
            srvowner = json.\("srvowner").as[Long],
            srvfdw = json.\("srvfdw").as[Long],
            srvtype = json.\("srvtype").toOption.map(_.as[String]),
            srvversion = json.\("srvversion").toOption.map(_.as[String]),
            srvacl = json.\("srvacl").toOption.map(_.as[Array[String]]),
            srvoptions = json.\("srvoptions").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
