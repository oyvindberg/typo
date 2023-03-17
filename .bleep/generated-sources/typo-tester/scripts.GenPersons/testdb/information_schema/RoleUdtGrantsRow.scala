package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class RoleUdtGrantsRow(
  /** Points to [[testdb.information_schema.UdtPrivilegesRow.grantor]] */
  grantor: Option[String],
  /** Points to [[testdb.information_schema.UdtPrivilegesRow.grantee]] */
  grantee: Option[String],
  /** Points to [[testdb.information_schema.UdtPrivilegesRow.udtCatalog]] */
  udtCatalog: Option[String],
  /** Points to [[testdb.information_schema.UdtPrivilegesRow.udtSchema]] */
  udtSchema: Option[String],
  /** Points to [[testdb.information_schema.UdtPrivilegesRow.udtName]] */
  udtName: Option[String],
  /** Points to [[testdb.information_schema.UdtPrivilegesRow.privilegeType]] */
  privilegeType: Option[String],
  /** Points to [[testdb.information_schema.UdtPrivilegesRow.isGrantable]] */
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

  implicit val oFormat: OFormat[RoleUdtGrantsRow] = new OFormat[RoleUdtGrantsRow]{
    override def writes(o: RoleUdtGrantsRow): JsObject =
      Json.obj(
        "grantor" -> o.grantor,
      "grantee" -> o.grantee,
      "udt_catalog" -> o.udtCatalog,
      "udt_schema" -> o.udtSchema,
      "udt_name" -> o.udtName,
      "privilege_type" -> o.privilegeType,
      "is_grantable" -> o.isGrantable
      )

    override def reads(json: JsValue): JsResult[RoleUdtGrantsRow] = {
      JsResult.fromTry(
        Try(
          RoleUdtGrantsRow(
            grantor = json.\("grantor").toOption.map(_.as[String]),
            grantee = json.\("grantee").toOption.map(_.as[String]),
            udtCatalog = json.\("udt_catalog").toOption.map(_.as[String]),
            udtSchema = json.\("udt_schema").toOption.map(_.as[String]),
            udtName = json.\("udt_name").toOption.map(_.as[String]),
            privilegeType = json.\("privilege_type").toOption.map(_.as[String]),
            isGrantable = json.\("is_grantable").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
