package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class SchemataRow(
  catalogName: /* unknown nullability */ Option[String],
  schemaName: /* unknown nullability */ Option[String],
  schemaOwner: /* unknown nullability */ Option[String],
  defaultCharacterSetCatalog: /* unknown nullability */ Option[String],
  defaultCharacterSetSchema: /* unknown nullability */ Option[String],
  defaultCharacterSetName: /* unknown nullability */ Option[String],
  sqlPath: /* unknown nullability */ Option[String]
)

object SchemataRow {
  implicit val rowParser: RowParser[SchemataRow] = { row =>
    Success(
      SchemataRow(
        catalogName = row[/* unknown nullability */ Option[String]]("catalog_name"),
        schemaName = row[/* unknown nullability */ Option[String]]("schema_name"),
        schemaOwner = row[/* unknown nullability */ Option[String]]("schema_owner"),
        defaultCharacterSetCatalog = row[/* unknown nullability */ Option[String]]("default_character_set_catalog"),
        defaultCharacterSetSchema = row[/* unknown nullability */ Option[String]]("default_character_set_schema"),
        defaultCharacterSetName = row[/* unknown nullability */ Option[String]]("default_character_set_name"),
        sqlPath = row[/* unknown nullability */ Option[String]]("sql_path")
      )
    )
  }

  implicit val oFormat: OFormat[SchemataRow] = Json.format
}
