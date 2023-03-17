package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class DataTypePrivilegesRow(
  objectCatalog: /* unknown nullability */ Option[String],
  objectSchema: /* unknown nullability */ Option[String],
  objectName: /* unknown nullability */ Option[String],
  objectType: /* unknown nullability */ Option[String],
  dtdIdentifier: /* unknown nullability */ Option[String]
)

object DataTypePrivilegesRow {
  implicit val rowParser: RowParser[DataTypePrivilegesRow] = { row =>
    Success(
      DataTypePrivilegesRow(
        objectCatalog = row[/* unknown nullability */ Option[String]]("object_catalog"),
        objectSchema = row[/* unknown nullability */ Option[String]]("object_schema"),
        objectName = row[/* unknown nullability */ Option[String]]("object_name"),
        objectType = row[/* unknown nullability */ Option[String]]("object_type"),
        dtdIdentifier = row[/* unknown nullability */ Option[String]]("dtd_identifier")
      )
    )
  }

  implicit val oFormat: OFormat[DataTypePrivilegesRow] = new OFormat[DataTypePrivilegesRow]{
    override def writes(o: DataTypePrivilegesRow): JsObject =
      Json.obj(
        "object_catalog" -> o.objectCatalog,
      "object_schema" -> o.objectSchema,
      "object_name" -> o.objectName,
      "object_type" -> o.objectType,
      "dtd_identifier" -> o.dtdIdentifier
      )

    override def reads(json: JsValue): JsResult[DataTypePrivilegesRow] = {
      JsResult.fromTry(
        Try(
          DataTypePrivilegesRow(
            objectCatalog = json.\("object_catalog").toOption.map(_.as[String]),
            objectSchema = json.\("object_schema").toOption.map(_.as[String]),
            objectName = json.\("object_name").toOption.map(_.as[String]),
            objectType = json.\("object_type").toOption.map(_.as[String]),
            dtdIdentifier = json.\("dtd_identifier").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
