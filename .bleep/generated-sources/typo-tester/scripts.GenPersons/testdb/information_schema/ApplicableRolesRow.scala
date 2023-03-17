package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ApplicableRolesRow(
  grantee: /* unknown nullability */ Option[String],
  roleName: /* unknown nullability */ Option[String],
  isGrantable: /* unknown nullability */ Option[String]
)

object ApplicableRolesRow {
  implicit val rowParser: RowParser[ApplicableRolesRow] = { row =>
    Success(
      ApplicableRolesRow(
        grantee = row[/* unknown nullability */ Option[String]]("grantee"),
        roleName = row[/* unknown nullability */ Option[String]]("role_name"),
        isGrantable = row[/* unknown nullability */ Option[String]]("is_grantable")
      )
    )
  }

  implicit val oFormat: OFormat[ApplicableRolesRow] = Json.format
}
