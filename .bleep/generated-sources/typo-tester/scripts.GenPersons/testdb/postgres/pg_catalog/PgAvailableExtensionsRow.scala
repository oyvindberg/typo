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

case class PgAvailableExtensionsRow(
  name: /* unknown nullability */ Option[String],
  defaultVersion: /* unknown nullability */ Option[String],
  /** Points to [[PgExtensionRow.extversion]] */
  installedVersion: String,
  comment: /* unknown nullability */ Option[String]
)

object PgAvailableExtensionsRow {
  implicit val rowParser: RowParser[PgAvailableExtensionsRow] = { row =>
    Success(
      PgAvailableExtensionsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        defaultVersion = row[/* unknown nullability */ Option[String]]("default_version"),
        installedVersion = row[String]("installed_version"),
        comment = row[/* unknown nullability */ Option[String]]("comment")
      )
    )
  }

  implicit val oFormat: OFormat[PgAvailableExtensionsRow] = new OFormat[PgAvailableExtensionsRow]{
    override def writes(o: PgAvailableExtensionsRow): JsObject =
      Json.obj(
        "name" -> o.name,
      "default_version" -> o.defaultVersion,
      "installed_version" -> o.installedVersion,
      "comment" -> o.comment
      )

    override def reads(json: JsValue): JsResult[PgAvailableExtensionsRow] = {
      JsResult.fromTry(
        Try(
          PgAvailableExtensionsRow(
            name = json.\("name").toOption.map(_.as[String]),
            defaultVersion = json.\("default_version").toOption.map(_.as[String]),
            installedVersion = json.\("installed_version").as[String],
            comment = json.\("comment").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
