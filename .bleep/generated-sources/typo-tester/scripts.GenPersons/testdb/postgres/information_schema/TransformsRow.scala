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

case class TransformsRow(
  udtCatalog: /* unknown nullability */ Option[String],
  udtSchema: /* unknown nullability */ Option[String],
  udtName: /* unknown nullability */ Option[String],
  specificCatalog: /* unknown nullability */ Option[String],
  specificSchema: /* unknown nullability */ Option[String],
  specificName: /* unknown nullability */ Option[String],
  groupName: /* unknown nullability */ Option[String],
  transformType: /* unknown nullability */ Option[String]
)

object TransformsRow {
  implicit val rowParser: RowParser[TransformsRow] = { row =>
    Success(
      TransformsRow(
        udtCatalog = row[/* unknown nullability */ Option[String]]("udt_catalog"),
        udtSchema = row[/* unknown nullability */ Option[String]]("udt_schema"),
        udtName = row[/* unknown nullability */ Option[String]]("udt_name"),
        specificCatalog = row[/* unknown nullability */ Option[String]]("specific_catalog"),
        specificSchema = row[/* unknown nullability */ Option[String]]("specific_schema"),
        specificName = row[/* unknown nullability */ Option[String]]("specific_name"),
        groupName = row[/* unknown nullability */ Option[String]]("group_name"),
        transformType = row[/* unknown nullability */ Option[String]]("transform_type")
      )
    )
  }

  implicit val oFormat: OFormat[TransformsRow] = new OFormat[TransformsRow]{
    override def writes(o: TransformsRow): JsObject =
      Json.obj(
        "udt_catalog" -> o.udtCatalog,
      "udt_schema" -> o.udtSchema,
      "udt_name" -> o.udtName,
      "specific_catalog" -> o.specificCatalog,
      "specific_schema" -> o.specificSchema,
      "specific_name" -> o.specificName,
      "group_name" -> o.groupName,
      "transform_type" -> o.transformType
      )

    override def reads(json: JsValue): JsResult[TransformsRow] = {
      JsResult.fromTry(
        Try(
          TransformsRow(
            udtCatalog = json.\("udt_catalog").toOption.map(_.as[String]),
            udtSchema = json.\("udt_schema").toOption.map(_.as[String]),
            udtName = json.\("udt_name").toOption.map(_.as[String]),
            specificCatalog = json.\("specific_catalog").toOption.map(_.as[String]),
            specificSchema = json.\("specific_schema").toOption.map(_.as[String]),
            specificName = json.\("specific_name").toOption.map(_.as[String]),
            groupName = json.\("group_name").toOption.map(_.as[String]),
            transformType = json.\("transform_type").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
