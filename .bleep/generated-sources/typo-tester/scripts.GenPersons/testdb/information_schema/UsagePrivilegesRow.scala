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

  implicit val oFormat: OFormat[UsagePrivilegesRow] = new OFormat[UsagePrivilegesRow]{
    override def writes(o: UsagePrivilegesRow): JsObject =
      Json.obj(
        "grantor" -> o.grantor,
      "grantee" -> o.grantee,
      "object_catalog" -> o.objectCatalog,
      "object_schema" -> o.objectSchema,
      "object_name" -> o.objectName,
      "object_type" -> o.objectType,
      "privilege_type" -> o.privilegeType,
      "is_grantable" -> o.isGrantable
      )

    override def reads(json: JsValue): JsResult[UsagePrivilegesRow] = {
      JsResult.fromTry(
        Try(
          UsagePrivilegesRow(
            grantor = json.\("grantor").toOption.map(_.as[String]),
            grantee = json.\("grantee").toOption.map(_.as[String]),
            objectCatalog = json.\("object_catalog").toOption.map(_.as[String]),
            objectSchema = json.\("object_schema").toOption.map(_.as[String]),
            objectName = json.\("object_name").toOption.map(_.as[String]),
            objectType = json.\("object_type").toOption.map(_.as[String]),
            privilegeType = json.\("privilege_type").toOption.map(_.as[String]),
            isGrantable = json.\("is_grantable").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
