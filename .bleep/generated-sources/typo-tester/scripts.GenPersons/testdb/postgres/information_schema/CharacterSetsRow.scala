package testdb
package postgres
package information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

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

  implicit val oFormat: OFormat[CharacterSetsRow] = new OFormat[CharacterSetsRow]{
    override def writes(o: CharacterSetsRow): JsObject =
      Json.obj(
        "character_set_catalog" -> o.characterSetCatalog,
      "character_set_schema" -> o.characterSetSchema,
      "character_set_name" -> o.characterSetName,
      "character_repertoire" -> o.characterRepertoire,
      "form_of_use" -> o.formOfUse,
      "default_collate_catalog" -> o.defaultCollateCatalog,
      "default_collate_schema" -> o.defaultCollateSchema,
      "default_collate_name" -> o.defaultCollateName
      )

    override def reads(json: JsValue): JsResult[CharacterSetsRow] = {
      JsResult.fromTry(
        Try(
          CharacterSetsRow(
            characterSetCatalog = json.\("character_set_catalog").toOption.map(_.as[String]),
            characterSetSchema = json.\("character_set_schema").toOption.map(_.as[String]),
            characterSetName = json.\("character_set_name").toOption.map(_.as[String]),
            characterRepertoire = json.\("character_repertoire").toOption.map(_.as[String]),
            formOfUse = json.\("form_of_use").toOption.map(_.as[String]),
            defaultCollateCatalog = json.\("default_collate_catalog").toOption.map(_.as[String]),
            defaultCollateSchema = json.\("default_collate_schema").toOption.map(_.as[String]),
            defaultCollateName = json.\("default_collate_name").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
