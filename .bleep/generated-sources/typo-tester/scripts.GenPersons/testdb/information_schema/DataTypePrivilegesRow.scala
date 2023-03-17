package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[DataTypePrivilegesRow] = Json.format
}
