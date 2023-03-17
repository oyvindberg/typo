package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class AdministrableRoleAuthorizationsRow(
  grantee: Option[String],
  roleName: Option[String],
  isGrantable: Option[String]
)

object AdministrableRoleAuthorizationsRow {
  implicit val rowParser: RowParser[AdministrableRoleAuthorizationsRow] = { row =>
    Success(
      AdministrableRoleAuthorizationsRow(
        grantee = row[Option[String]]("grantee"),
        roleName = row[Option[String]]("role_name"),
        isGrantable = row[Option[String]]("is_grantable")
      )
    )
  }

  implicit val oFormat: OFormat[AdministrableRoleAuthorizationsRow] = Json.format
}
