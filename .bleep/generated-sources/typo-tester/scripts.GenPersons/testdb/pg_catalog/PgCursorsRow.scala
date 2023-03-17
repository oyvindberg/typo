package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgCursorsRow(
  name: /* unknown nullability */ Option[String],
  statement: /* unknown nullability */ Option[String],
  isHoldable: /* unknown nullability */ Option[Boolean],
  isBinary: /* unknown nullability */ Option[Boolean],
  isScrollable: /* unknown nullability */ Option[Boolean],
  creationTime: /* unknown nullability */ Option[LocalDateTime]
)

object PgCursorsRow {
  implicit val rowParser: RowParser[PgCursorsRow] = { row =>
    Success(
      PgCursorsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        statement = row[/* unknown nullability */ Option[String]]("statement"),
        isHoldable = row[/* unknown nullability */ Option[Boolean]]("is_holdable"),
        isBinary = row[/* unknown nullability */ Option[Boolean]]("is_binary"),
        isScrollable = row[/* unknown nullability */ Option[Boolean]]("is_scrollable"),
        creationTime = row[/* unknown nullability */ Option[LocalDateTime]]("creation_time")
      )
    )
  }

  implicit val oFormat: OFormat[PgCursorsRow] = new OFormat[PgCursorsRow]{
    override def writes(o: PgCursorsRow): JsObject =
      Json.obj(
        "name" -> o.name,
      "statement" -> o.statement,
      "is_holdable" -> o.isHoldable,
      "is_binary" -> o.isBinary,
      "is_scrollable" -> o.isScrollable,
      "creation_time" -> o.creationTime
      )

    override def reads(json: JsValue): JsResult[PgCursorsRow] = {
      JsResult.fromTry(
        Try(
          PgCursorsRow(
            name = json.\("name").toOption.map(_.as[String]),
            statement = json.\("statement").toOption.map(_.as[String]),
            isHoldable = json.\("is_holdable").toOption.map(_.as[Boolean]),
            isBinary = json.\("is_binary").toOption.map(_.as[Boolean]),
            isScrollable = json.\("is_scrollable").toOption.map(_.as[Boolean]),
            creationTime = json.\("creation_time").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}
