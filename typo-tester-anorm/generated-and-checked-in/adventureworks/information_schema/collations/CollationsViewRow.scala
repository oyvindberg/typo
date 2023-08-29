/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package collations

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

case class CollationsViewRow(
  collationCatalog: /* nullability unknown */ Option[String],
  collationSchema: /* nullability unknown */ Option[String],
  collationName: /* nullability unknown */ Option[String],
  padAttribute: /* nullability unknown */ Option[String]
)

object CollationsViewRow {
  implicit lazy val reads: Reads[CollationsViewRow] = Reads[CollationsViewRow](json => JsResult.fromTry(
      Try(
        CollationsViewRow(
          collationCatalog = json.\("collation_catalog").toOption.map(_.as(Reads.StringReads)),
          collationSchema = json.\("collation_schema").toOption.map(_.as(Reads.StringReads)),
          collationName = json.\("collation_name").toOption.map(_.as(Reads.StringReads)),
          padAttribute = json.\("pad_attribute").toOption.map(_.as(Reads.StringReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[CollationsViewRow] = RowParser[CollationsViewRow] { row =>
    Success(
      CollationsViewRow(
        collationCatalog = row(idx + 0)(Column.columnToOption(Column.columnToString)),
        collationSchema = row(idx + 1)(Column.columnToOption(Column.columnToString)),
        collationName = row(idx + 2)(Column.columnToOption(Column.columnToString)),
        padAttribute = row(idx + 3)(Column.columnToOption(Column.columnToString))
      )
    )
  }
  implicit lazy val writes: OWrites[CollationsViewRow] = OWrites[CollationsViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "collation_catalog" -> Writes.OptionWrites(Writes.StringWrites).writes(o.collationCatalog),
      "collation_schema" -> Writes.OptionWrites(Writes.StringWrites).writes(o.collationSchema),
      "collation_name" -> Writes.OptionWrites(Writes.StringWrites).writes(o.collationName),
      "pad_attribute" -> Writes.OptionWrites(Writes.StringWrites).writes(o.padAttribute)
    ))
  )
}