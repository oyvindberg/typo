/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package collations

import adventureworks.information_schema.CharacterData
import adventureworks.information_schema.SqlIdentifier
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class CollationsViewRow(
  collationCatalog: Option[SqlIdentifier],
  collationSchema: Option[SqlIdentifier],
  collationName: Option[SqlIdentifier],
  padAttribute: Option[CharacterData]
)

object CollationsViewRow {
  def rowParser(idx: Int): RowParser[CollationsViewRow] =
    RowParser[CollationsViewRow] { row =>
      Success(
        CollationsViewRow(
          collationCatalog = row[Option[SqlIdentifier]](idx + 0),
          collationSchema = row[Option[SqlIdentifier]](idx + 1),
          collationName = row[Option[SqlIdentifier]](idx + 2),
          padAttribute = row[Option[CharacterData]](idx + 3)
        )
      )
    }
  implicit val oFormat: OFormat[CollationsViewRow] = new OFormat[CollationsViewRow]{
    override def writes(o: CollationsViewRow): JsObject =
      Json.obj(
        "collation_catalog" -> o.collationCatalog,
        "collation_schema" -> o.collationSchema,
        "collation_name" -> o.collationName,
        "pad_attribute" -> o.padAttribute
      )
  
    override def reads(json: JsValue): JsResult[CollationsViewRow] = {
      JsResult.fromTry(
        Try(
          CollationsViewRow(
            collationCatalog = json.\("collation_catalog").toOption.map(_.as[SqlIdentifier]),
            collationSchema = json.\("collation_schema").toOption.map(_.as[SqlIdentifier]),
            collationName = json.\("collation_name").toOption.map(_.as[SqlIdentifier]),
            padAttribute = json.\("pad_attribute").toOption.map(_.as[CharacterData])
          )
        )
      )
    }
  }
}