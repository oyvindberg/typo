package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class RoleUsageGrantsRow(
  grantor: Option[String],
  grantee: Option[String],
  objectCatalog: Option[String],
  objectSchema: Option[String],
  objectName: Option[String],
  objectType: Option[String],
  privilegeType: Option[String],
  isGrantable: Option[String]
)

object RoleUsageGrantsRow {
  implicit val rowParser: RowParser[RoleUsageGrantsRow] = { row =>
    Success(
      RoleUsageGrantsRow(
        grantor = row[Option[String]]("grantor"),
        grantee = row[Option[String]]("grantee"),
        objectCatalog = row[Option[String]]("object_catalog"),
        objectSchema = row[Option[String]]("object_schema"),
        objectName = row[Option[String]]("object_name"),
        objectType = row[Option[String]]("object_type"),
        privilegeType = row[Option[String]]("privilege_type"),
        isGrantable = row[Option[String]]("is_grantable")
      )
    )
  }

  implicit val oFormat: OFormat[RoleUsageGrantsRow] = Json.format
}
