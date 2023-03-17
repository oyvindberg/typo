package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[PgAvailableExtensionVersionsRow] = Json.format
}
