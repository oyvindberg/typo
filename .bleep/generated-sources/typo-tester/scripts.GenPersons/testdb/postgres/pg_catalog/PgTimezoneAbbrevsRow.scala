package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import org.postgresql.util.PGInterval
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgTimezoneAbbrevsRow(
  abbrev: /* unknown nullability */ Option[String],
  utcOffset: /* unknown nullability */ Option[/* interval */ PGInterval],
  isDst: /* unknown nullability */ Option[Boolean]
)

object PgTimezoneAbbrevsRow {
  implicit val rowParser: RowParser[PgTimezoneAbbrevsRow] = { row =>
    Success(
      PgTimezoneAbbrevsRow(
        abbrev = row[/* unknown nullability */ Option[String]]("abbrev"),
        utcOffset = row[/* unknown nullability */ Option[/* interval */ PGInterval]]("utc_offset"),
        isDst = row[/* unknown nullability */ Option[Boolean]]("is_dst")
      )
    )
  }

  implicit val oFormat: OFormat[PgTimezoneAbbrevsRow] = new OFormat[PgTimezoneAbbrevsRow]{
    override def writes(o: PgTimezoneAbbrevsRow): JsObject =
      Json.obj(
        "abbrev" -> o.abbrev,
      "utc_offset" -> o.utcOffset,
      "is_dst" -> o.isDst
      )

    override def reads(json: JsValue): JsResult[PgTimezoneAbbrevsRow] = {
      JsResult.fromTry(
        Try(
          PgTimezoneAbbrevsRow(
            abbrev = json.\("abbrev").toOption.map(_.as[String]),
            utcOffset = json.\("utc_offset").toOption.map(_.as[/* interval */ PGInterval]),
            isDst = json.\("is_dst").toOption.map(_.as[Boolean])
          )
        )
      )
    }
  }
}
