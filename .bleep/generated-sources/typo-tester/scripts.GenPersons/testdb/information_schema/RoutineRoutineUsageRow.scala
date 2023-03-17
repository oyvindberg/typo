package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class RoutineRoutineUsageRow(
  specificCatalog: /* unknown nullability */ Option[String],
  specificSchema: /* unknown nullability */ Option[String],
  specificName: /* unknown nullability */ Option[String],
  routineCatalog: /* unknown nullability */ Option[String],
  routineSchema: /* unknown nullability */ Option[String],
  routineName: /* unknown nullability */ Option[String]
)

object RoutineRoutineUsageRow {
  implicit val rowParser: RowParser[RoutineRoutineUsageRow] = { row =>
    Success(
      RoutineRoutineUsageRow(
        specificCatalog = row[/* unknown nullability */ Option[String]]("specific_catalog"),
        specificSchema = row[/* unknown nullability */ Option[String]]("specific_schema"),
        specificName = row[/* unknown nullability */ Option[String]]("specific_name"),
        routineCatalog = row[/* unknown nullability */ Option[String]]("routine_catalog"),
        routineSchema = row[/* unknown nullability */ Option[String]]("routine_schema"),
        routineName = row[/* unknown nullability */ Option[String]]("routine_name")
      )
    )
  }

  implicit val oFormat: OFormat[RoutineRoutineUsageRow] = Json.format
}
