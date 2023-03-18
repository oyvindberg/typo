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

case class CollationsRow(
  collationCatalog: /* unknown nullability */ Option[String],
  collationSchema: /* unknown nullability */ Option[String],
  collationName: /* unknown nullability */ Option[String],
  padAttribute: /* unknown nullability */ Option[String]
)

object CollationsRow {
  implicit val rowParser: RowParser[CollationsRow] = { row =>
    Success(
      CollationsRow(
        collationCatalog = row[/* unknown nullability */ Option[String]]("collation_catalog"),
        collationSchema = row[/* unknown nullability */ Option[String]]("collation_schema"),
        collationName = row[/* unknown nullability */ Option[String]]("collation_name"),
        padAttribute = row[/* unknown nullability */ Option[String]]("pad_attribute")
      )
    )
  }

  implicit val oFormat: OFormat[CollationsRow] = new OFormat[CollationsRow]{
    override def writes(o: CollationsRow): JsObject =
      Json.obj(
        "collation_catalog" -> o.collationCatalog,
      "collation_schema" -> o.collationSchema,
      "collation_name" -> o.collationName,
      "pad_attribute" -> o.padAttribute
      )

    override def reads(json: JsValue): JsResult[CollationsRow] = {
      JsResult.fromTry(
        Try(
          CollationsRow(
            collationCatalog = json.\("collation_catalog").toOption.map(_.as[String]),
            collationSchema = json.\("collation_schema").toOption.map(_.as[String]),
            collationName = json.\("collation_name").toOption.map(_.as[String]),
            padAttribute = json.\("pad_attribute").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
