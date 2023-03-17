package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatGssapiRow(
  pid: /* unknown nullability */ Option[Int],
  gssAuthenticated: /* unknown nullability */ Option[Boolean],
  principal: /* unknown nullability */ Option[String],
  encrypted: /* unknown nullability */ Option[Boolean]
)

object PgStatGssapiRow {
  implicit val rowParser: RowParser[PgStatGssapiRow] = { row =>
    Success(
      PgStatGssapiRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        gssAuthenticated = row[/* unknown nullability */ Option[Boolean]]("gss_authenticated"),
        principal = row[/* unknown nullability */ Option[String]]("principal"),
        encrypted = row[/* unknown nullability */ Option[Boolean]]("encrypted")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatGssapiRow] = new OFormat[PgStatGssapiRow]{
    override def writes(o: PgStatGssapiRow): JsObject =
      Json.obj(
        "pid" -> o.pid,
      "gss_authenticated" -> o.gssAuthenticated,
      "principal" -> o.principal,
      "encrypted" -> o.encrypted
      )

    override def reads(json: JsValue): JsResult[PgStatGssapiRow] = {
      JsResult.fromTry(
        Try(
          PgStatGssapiRow(
            pid = json.\("pid").toOption.map(_.as[Int]),
            gssAuthenticated = json.\("gss_authenticated").toOption.map(_.as[Boolean]),
            principal = json.\("principal").toOption.map(_.as[String]),
            encrypted = json.\("encrypted").toOption.map(_.as[Boolean])
          )
        )
      )
    }
  }
}
