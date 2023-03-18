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

  implicit val oFormat: OFormat[EnabledRolesRow] = new OFormat[EnabledRolesRow]{
    override def writes(o: EnabledRolesRow): JsObject =
      Json.obj(
        "role_name" -> o.roleName
      )

    override def reads(json: JsValue): JsResult[EnabledRolesRow] = {
      JsResult.fromTry(
        Try(
          EnabledRolesRow(
            roleName = json.\("role_name").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
