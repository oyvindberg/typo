package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class UsagePrivilegesRow(
  grantor: /* unknown nullability */ Option[String],
  grantee: /* unknown nullability */ Option[String],
  objectCatalog: /* unknown nullability */ Option[String],
  objectSchema: /* unknown nullability */ Option[String],
  objectName: /* unknown nullability */ Option[String],
  objectType: /* unknown nullability */ Option[String],
  privilegeType: /* unknown nullability */ Option[String],
  isGrantable: /* unknown nullability */ Option[String]
)

object UsagePrivilegesRow {
  implicit val rowParser: RowParser[UsagePrivilegesRow] = { row =>
    Success(
      UsagePrivilegesRow(
        grantor = row[/* unknown nullability */ Option[String]]("grantor"),
        grantee = row[/* unknown nullability */ Option[String]]("grantee"),
        objectCatalog = row[/* unknown nullability */ Option[String]]("object_catalog"),
        objectSchema = row[/* unknown nullability */ Option[String]]("object_schema"),
        objectName = row[/* unknown nullability */ Option[String]]("object_name"),
        objectType = row[/* unknown nullability */ Option[String]]("object_type"),
        privilegeType = row[/* unknown nullability */ Option[String]]("privilege_type"),
        isGrantable = row[/* unknown nullability */ Option[String]]("is_grantable")
      )
    )
  }

  implicit val oFormat: OFormat[UsagePrivilegesRow] = Json.format
}
