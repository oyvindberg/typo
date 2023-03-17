package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class RoleRoutineGrantsRow(
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.grantor]] */
  grantor: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.grantee]] */
  grantee: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.specificCatalog]] */
  specificCatalog: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.specificSchema]] */
  specificSchema: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.specificName]] */
  specificName: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.routineCatalog]] */
  routineCatalog: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.routineSchema]] */
  routineSchema: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.routineName]] */
  routineName: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.privilegeType]] */
  privilegeType: Option[String],
  /** Points to [[testdb.information_schema.RoutinePrivilegesRow.isGrantable]] */
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

  implicit val oFormat: OFormat[RoleRoutineGrantsRow] = Json.format
}
