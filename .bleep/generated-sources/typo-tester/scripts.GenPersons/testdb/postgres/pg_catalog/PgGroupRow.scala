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

case class PgGroupRow(
  /** Points to [[PgAuthidRow.rolname]] */
  groname: String,
  /** Points to [[PgAuthidRow.oid]] */
  grosysid: Long,
  grolist: /* unknown nullability */ Option[Array[Long]]
)

object PgGroupRow {
  implicit val rowParser: RowParser[PgGroupRow] = { row =>
    Success(
      PgGroupRow(
        groname = row[String]("groname"),
        grosysid = row[Long]("grosysid"),
        grolist = row[/* unknown nullability */ Option[Array[Long]]]("grolist")
      )
    )
  }

  implicit val oFormat: OFormat[PgGroupRow] = new OFormat[PgGroupRow]{
    override def writes(o: PgGroupRow): JsObject =
      Json.obj(
        "groname" -> o.groname,
      "grosysid" -> o.grosysid,
      "grolist" -> o.grolist
      )

    override def reads(json: JsValue): JsResult[PgGroupRow] = {
      JsResult.fromTry(
        Try(
          PgGroupRow(
            groname = json.\("groname").as[String],
            grosysid = json.\("grosysid").as[Long],
            grolist = json.\("grolist").toOption.map(_.as[Array[Long]])
          )
        )
      )
    }
  }
}
