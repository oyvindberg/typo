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

case class PgTablespaceRow(
  oid: PgTablespaceId,
  spcname: String,
  spcowner: Long,
  spcacl: Option[Array[String]],
  spcoptions: Option[Array[String]]
)

object PgTablespaceRow {
  implicit val rowParser: RowParser[PgTablespaceRow] = { row =>
    Success(
      PgTablespaceRow(
        oid = row[PgTablespaceId]("oid"),
        spcname = row[String]("spcname"),
        spcowner = row[Long]("spcowner"),
        spcacl = row[Option[Array[String]]]("spcacl"),
        spcoptions = row[Option[Array[String]]]("spcoptions")
      )
    )
  }

  implicit val oFormat: OFormat[PgTablespaceRow] = new OFormat[PgTablespaceRow]{
    override def writes(o: PgTablespaceRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "spcname" -> o.spcname,
      "spcowner" -> o.spcowner,
      "spcacl" -> o.spcacl,
      "spcoptions" -> o.spcoptions
      )

    override def reads(json: JsValue): JsResult[PgTablespaceRow] = {
      JsResult.fromTry(
        Try(
          PgTablespaceRow(
            oid = json.\("oid").as[PgTablespaceId],
            spcname = json.\("spcname").as[String],
            spcowner = json.\("spcowner").as[Long],
            spcacl = json.\("spcacl").toOption.map(_.as[Array[String]]),
            spcoptions = json.\("spcoptions").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
