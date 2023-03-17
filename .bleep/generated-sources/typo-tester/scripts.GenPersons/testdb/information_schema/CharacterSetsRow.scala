package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class CharacterSetsRow(
  characterSetCatalog: /* unknown nullability */ Option[String],
  characterSetSchema: /* unknown nullability */ Option[String],
  characterSetName: /* unknown nullability */ Option[String],
  characterRepertoire: /* unknown nullability */ Option[String],
  formOfUse: /* unknown nullability */ Option[String],
  defaultCollateCatalog: /* unknown nullability */ Option[String],
  defaultCollateSchema: /* unknown nullability */ Option[String],
  defaultCollateName: /* unknown nullability */ Option[String]
)

object CharacterSetsRow {
  implicit val rowParser: RowParser[CharacterSetsRow] = { row =>
    Success(
      CharacterSetsRow(
        characterSetCatalog = row[/* unknown nullability */ Option[String]]("character_set_catalog"),
        characterSetSchema = row[/* unknown nullability */ Option[String]]("character_set_schema"),
        characterSetName = row[/* unknown nullability */ Option[String]]("character_set_name"),
        characterRepertoire = row[/* unknown nullability */ Option[String]]("character_repertoire"),
        formOfUse = row[/* unknown nullability */ Option[String]]("form_of_use"),
        defaultCollateCatalog = row[/* unknown nullability */ Option[String]]("default_collate_catalog"),
        defaultCollateSchema = row[/* unknown nullability */ Option[String]]("default_collate_schema"),
        defaultCollateName = row[/* unknown nullability */ Option[String]]("default_collate_name")
      )
    )
  }

  implicit val oFormat: OFormat[CharacterSetsRow] = Json.format
}
