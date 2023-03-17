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

case class PgAvailableExtensionVersionsRow(
  name: /* unknown nullability */ Option[String],
  version: /* unknown nullability */ Option[String],
  installed: /* unknown nullability */ Option[Boolean],
  superuser: /* unknown nullability */ Option[Boolean],
  trusted: /* unknown nullability */ Option[Boolean],
  relocatable: /* unknown nullability */ Option[Boolean],
  schema: /* unknown nullability */ Option[String],
  requires: /* typo doesn't know how to translate: columnType: Array, columnTypeName: _name, columnClassName: java.sql.Array */ Any,
  comment: /* unknown nullability */ Option[String]
)

object PgAvailableExtensionVersionsRow {
  implicit val rowParser: RowParser[PgAvailableExtensionVersionsRow] = { row =>
    Success(
      PgAvailableExtensionVersionsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        version = row[/* unknown nullability */ Option[String]]("version"),
        installed = row[/* unknown nullability */ Option[Boolean]]("installed"),
        superuser = row[/* unknown nullability */ Option[Boolean]]("superuser"),
        trusted = row[/* unknown nullability */ Option[Boolean]]("trusted"),
        relocatable = row[/* unknown nullability */ Option[Boolean]]("relocatable"),
        schema = row[/* unknown nullability */ Option[String]]("schema"),
        requires = row[/* typo doesn't know how to translate: columnType: Array, columnTypeName: _name, columnClassName: java.sql.Array */ Any]("requires"),
        comment = row[/* unknown nullability */ Option[String]]("comment")
      )
    )
  }

  implicit val oFormat: OFormat[PgAvailableExtensionVersionsRow] = new OFormat[PgAvailableExtensionVersionsRow]{
    override def writes(o: PgAvailableExtensionVersionsRow): JsObject =
      Json.obj(
        "name" -> o.name,
      "version" -> o.version,
      "installed" -> o.installed,
      "superuser" -> o.superuser,
      "trusted" -> o.trusted,
      "relocatable" -> o.relocatable,
      "schema" -> o.schema,
      "requires" -> o.requires,
      "comment" -> o.comment
      )

    override def reads(json: JsValue): JsResult[PgAvailableExtensionVersionsRow] = {
      JsResult.fromTry(
        Try(
          PgAvailableExtensionVersionsRow(
            name = json.\("name").toOption.map(_.as[String]),
            version = json.\("version").toOption.map(_.as[String]),
            installed = json.\("installed").toOption.map(_.as[Boolean]),
            superuser = json.\("superuser").toOption.map(_.as[Boolean]),
            trusted = json.\("trusted").toOption.map(_.as[Boolean]),
            relocatable = json.\("relocatable").toOption.map(_.as[Boolean]),
            schema = json.\("schema").toOption.map(_.as[String]),
            requires = json.\("requires").as[/* typo doesn't know how to translate: columnType: Array, columnTypeName: _name, columnClassName: java.sql.Array */ Any],
            comment = json.\("comment").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
