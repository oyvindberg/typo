package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class RoleUdtGrantsRow(
  grantor: Option[String],
  grantee: Option[String],
  udtCatalog: Option[String],
  udtSchema: Option[String],
  udtName: Option[String],
  privilegeType: Option[String],
  isGrantable: Option[String]
)

object RoleUdtGrantsRow {
  implicit val rowParser: RowParser[RoleUdtGrantsRow] = { row =>
    Success(
      RoleUdtGrantsRow(
        grantor = row[Option[String]]("grantor"),
        grantee = row[Option[String]]("grantee"),
        udtCatalog = row[Option[String]]("udt_catalog"),
        udtSchema = row[Option[String]]("udt_schema"),
        udtName = row[Option[String]]("udt_name"),
        privilegeType = row[Option[String]]("privilege_type"),
        isGrantable = row[Option[String]]("is_grantable")
      )
    )
  }

  implicit val oFormat: OFormat[RoleUdtGrantsRow] = Json.format
}
