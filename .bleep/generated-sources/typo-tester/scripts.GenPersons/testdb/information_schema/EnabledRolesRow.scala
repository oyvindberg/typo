package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class EnabledRolesRow(
  roleName: /* unknown nullability */ Option[String]
)

object EnabledRolesRow {
  implicit val rowParser: RowParser[EnabledRolesRow] = { row =>
    Success(
      EnabledRolesRow(
        roleName = row[/* unknown nullability */ Option[String]]("role_name")
      )
    )
  }

  implicit val oFormat: OFormat[EnabledRolesRow] = Json.format
}
