/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package information_schema_catalog_name

import adventureworks.information_schema.SqlIdentifier
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class InformationSchemaCatalogNameViewRow(
  catalogName: Option[SqlIdentifier]
)

object InformationSchemaCatalogNameViewRow {
  def rowParser(idx: Int): RowParser[InformationSchemaCatalogNameViewRow] =
    RowParser[InformationSchemaCatalogNameViewRow] { row =>
      Success(
        InformationSchemaCatalogNameViewRow(
          catalogName = row[Option[SqlIdentifier]](idx + 0)
        )
      )
    }
  implicit val oFormat: OFormat[InformationSchemaCatalogNameViewRow] = new OFormat[InformationSchemaCatalogNameViewRow]{
    override def writes(o: InformationSchemaCatalogNameViewRow): JsObject =
      Json.obj(
        "catalog_name" -> o.catalogName
      )
  
    override def reads(json: JsValue): JsResult[InformationSchemaCatalogNameViewRow] = {
      JsResult.fromTry(
        Try(
          InformationSchemaCatalogNameViewRow(
            catalogName = json.\("catalog_name").toOption.map(_.as[SqlIdentifier])
          )
        )
      )
    }
  }
}