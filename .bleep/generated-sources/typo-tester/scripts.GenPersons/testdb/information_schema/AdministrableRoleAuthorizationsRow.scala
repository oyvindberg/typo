package testdb
package information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class AdministrableRoleAuthorizationsRow(
  /** Points to [[testdb.information_schema.ApplicableRolesRow.grantee]] */
  grantee: Option[String],
  /** Points to [[testdb.information_schema.ApplicableRolesRow.roleName]] */
  roleName: Option[String],
  /** Points to [[testdb.information_schema.ApplicableRolesRow.isGrantable]] */
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

  implicit val oFormat: OFormat[AdministrableRoleAuthorizationsRow] = new OFormat[AdministrableRoleAuthorizationsRow]{
    override def writes(o: AdministrableRoleAuthorizationsRow): JsObject =
      Json.obj(
        "grantee" -> o.grantee,
      "role_name" -> o.roleName,
      "is_grantable" -> o.isGrantable
      )

    override def reads(json: JsValue): JsResult[AdministrableRoleAuthorizationsRow] = {
      JsResult.fromTry(
        Try(
          AdministrableRoleAuthorizationsRow(
            grantee = json.\("grantee").toOption.map(_.as[String]),
            roleName = json.\("role_name").toOption.map(_.as[String]),
            isGrantable = json.\("is_grantable").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
