/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package collation_character_set_applicability

import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class CollationCharacterSetApplicabilityViewRow(
  collationCatalog: /* nullability unknown */ Option[String],
  collationSchema: /* nullability unknown */ Option[String],
  collationName: /* nullability unknown */ Option[String],
  characterSetCatalog: /* nullability unknown */ Option[String],
  characterSetSchema: /* nullability unknown */ Option[String],
  characterSetName: /* nullability unknown */ Option[String]
)

object CollationCharacterSetApplicabilityViewRow {
  implicit lazy val reads: Reads[CollationCharacterSetApplicabilityViewRow] = Reads[CollationCharacterSetApplicabilityViewRow](json => JsResult.fromTry(
      Try(
        CollationCharacterSetApplicabilityViewRow(
          collationCatalog = json.\("collation_catalog").toOption.map(_.as(Reads.StringReads)),
          collationSchema = json.\("collation_schema").toOption.map(_.as(Reads.StringReads)),
          collationName = json.\("collation_name").toOption.map(_.as(Reads.StringReads)),
          characterSetCatalog = json.\("character_set_catalog").toOption.map(_.as(Reads.StringReads)),
          characterSetSchema = json.\("character_set_schema").toOption.map(_.as(Reads.StringReads)),
          characterSetName = json.\("character_set_name").toOption.map(_.as(Reads.StringReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[CollationCharacterSetApplicabilityViewRow] = RowParser[CollationCharacterSetApplicabilityViewRow] { row =>
    Success(
      CollationCharacterSetApplicabilityViewRow(
        collationCatalog = row(idx + 0)(Column.columnToOption(Column.columnToString)),
        collationSchema = row(idx + 1)(Column.columnToOption(Column.columnToString)),
        collationName = row(idx + 2)(Column.columnToOption(Column.columnToString)),
        characterSetCatalog = row(idx + 3)(Column.columnToOption(Column.columnToString)),
        characterSetSchema = row(idx + 4)(Column.columnToOption(Column.columnToString)),
        characterSetName = row(idx + 5)(Column.columnToOption(Column.columnToString))
      )
    )
  }
  implicit lazy val writes: OWrites[CollationCharacterSetApplicabilityViewRow] = OWrites[CollationCharacterSetApplicabilityViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "collation_catalog" -> Writes.OptionWrites(Writes.StringWrites).writes(o.collationCatalog),
      "collation_schema" -> Writes.OptionWrites(Writes.StringWrites).writes(o.collationSchema),
      "collation_name" -> Writes.OptionWrites(Writes.StringWrites).writes(o.collationName),
      "character_set_catalog" -> Writes.OptionWrites(Writes.StringWrites).writes(o.characterSetCatalog),
      "character_set_schema" -> Writes.OptionWrites(Writes.StringWrites).writes(o.characterSetSchema),
      "character_set_name" -> Writes.OptionWrites(Writes.StringWrites).writes(o.characterSetName)
    ))
  )
}