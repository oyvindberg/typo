package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class RoleRoutineGrantsRow(
  grantor: Option[String],
  grantee: Option[String],
  specificCatalog: Option[String],
  specificSchema: Option[String],
  specificName: Option[String],
  routineCatalog: Option[String],
  routineSchema: Option[String],
  routineName: Option[String],
  privilegeType: Option[String],
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
