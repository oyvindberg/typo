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

case class RoleRoutineGrantsRow(
  /** Points to [[RoutinePrivilegesRow.grantor]] */
  grantor: Option[String],
  /** Points to [[RoutinePrivilegesRow.grantee]] */
  grantee: Option[String],
  /** Points to [[RoutinePrivilegesRow.specificCatalog]] */
  specificCatalog: Option[String],
  /** Points to [[RoutinePrivilegesRow.specificSchema]] */
  specificSchema: Option[String],
  /** Points to [[RoutinePrivilegesRow.specificName]] */
  specificName: Option[String],
  /** Points to [[RoutinePrivilegesRow.routineCatalog]] */
  routineCatalog: Option[String],
  /** Points to [[RoutinePrivilegesRow.routineSchema]] */
  routineSchema: Option[String],
  /** Points to [[RoutinePrivilegesRow.routineName]] */
  routineName: Option[String],
  /** Points to [[RoutinePrivilegesRow.privilegeType]] */
  privilegeType: Option[String],
  /** Points to [[RoutinePrivilegesRow.isGrantable]] */
  isGrantable: Option[String]
)

object RoleRoutineGrantsRow {
  implicit val rowParser: RowParser[RoleRoutineGrantsRow] = { row =>
    Success(
      RoleRoutineGrantsRow(
        grantor = row[Option[String]]("grantor"),
        grantee = row[Option[String]]("grantee"),
        specificCatalog = row[Option[String]]("specific_catalog"),
        specificSchema = row[Option[String]]("specific_schema"),
        specificName = row[Option[String]]("specific_name"),
        routineCatalog = row[Option[String]]("routine_catalog"),
        routineSchema = row[Option[String]]("routine_schema"),
        routineName = row[Option[String]]("routine_name"),
        privilegeType = row[Option[String]]("privilege_type"),
        isGrantable = row[Option[String]]("is_grantable")
      )
    )
  }

  implicit val oFormat: OFormat[RoleRoutineGrantsRow] = new OFormat[RoleRoutineGrantsRow]{
    override def writes(o: RoleRoutineGrantsRow): JsObject =
      Json.obj(
        "grantor" -> o.grantor,
      "grantee" -> o.grantee,
      "specific_catalog" -> o.specificCatalog,
      "specific_schema" -> o.specificSchema,
      "specific_name" -> o.specificName,
      "routine_catalog" -> o.routineCatalog,
      "routine_schema" -> o.routineSchema,
      "routine_name" -> o.routineName,
      "privilege_type" -> o.privilegeType,
      "is_grantable" -> o.isGrantable
      )

    override def reads(json: JsValue): JsResult[RoleRoutineGrantsRow] = {
      JsResult.fromTry(
        Try(
          RoleRoutineGrantsRow(
            grantor = json.\("grantor").toOption.map(_.as[String]),
            grantee = json.\("grantee").toOption.map(_.as[String]),
            specificCatalog = json.\("specific_catalog").toOption.map(_.as[String]),
            specificSchema = json.\("specific_schema").toOption.map(_.as[String]),
            specificName = json.\("specific_name").toOption.map(_.as[String]),
            routineCatalog = json.\("routine_catalog").toOption.map(_.as[String]),
            routineSchema = json.\("routine_schema").toOption.map(_.as[String]),
            routineName = json.\("routine_name").toOption.map(_.as[String]),
            privilegeType = json.\("privilege_type").toOption.map(_.as[String]),
            isGrantable = json.\("is_grantable").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
