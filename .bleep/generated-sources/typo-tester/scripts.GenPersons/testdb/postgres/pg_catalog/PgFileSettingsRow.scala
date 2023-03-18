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

case class PgFileSettingsRow(
  sourcefile: /* unknown nullability */ Option[String],
  sourceline: /* unknown nullability */ Option[Int],
  seqno: /* unknown nullability */ Option[Int],
  name: /* unknown nullability */ Option[String],
  setting: /* unknown nullability */ Option[String],
  applied: /* unknown nullability */ Option[Boolean],
  error: /* unknown nullability */ Option[String]
)

object PgFileSettingsRow {
  implicit val rowParser: RowParser[PgFileSettingsRow] = { row =>
    Success(
      PgFileSettingsRow(
        sourcefile = row[/* unknown nullability */ Option[String]]("sourcefile"),
        sourceline = row[/* unknown nullability */ Option[Int]]("sourceline"),
        seqno = row[/* unknown nullability */ Option[Int]]("seqno"),
        name = row[/* unknown nullability */ Option[String]]("name"),
        setting = row[/* unknown nullability */ Option[String]]("setting"),
        applied = row[/* unknown nullability */ Option[Boolean]]("applied"),
        error = row[/* unknown nullability */ Option[String]]("error")
      )
    )
  }

  implicit val oFormat: OFormat[PgFileSettingsRow] = new OFormat[PgFileSettingsRow]{
    override def writes(o: PgFileSettingsRow): JsObject =
      Json.obj(
        "sourcefile" -> o.sourcefile,
      "sourceline" -> o.sourceline,
      "seqno" -> o.seqno,
      "name" -> o.name,
      "setting" -> o.setting,
      "applied" -> o.applied,
      "error" -> o.error
      )

    override def reads(json: JsValue): JsResult[PgFileSettingsRow] = {
      JsResult.fromTry(
        Try(
          PgFileSettingsRow(
            sourcefile = json.\("sourcefile").toOption.map(_.as[String]),
            sourceline = json.\("sourceline").toOption.map(_.as[Int]),
            seqno = json.\("seqno").toOption.map(_.as[Int]),
            name = json.\("name").toOption.map(_.as[String]),
            setting = json.\("setting").toOption.map(_.as[String]),
            applied = json.\("applied").toOption.map(_.as[Boolean]),
            error = json.\("error").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
