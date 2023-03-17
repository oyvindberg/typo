package testdb.information_schema

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class TriggersRow(
  triggerCatalog: /* unknown nullability */ Option[String],
  triggerSchema: /* unknown nullability */ Option[String],
  triggerName: /* unknown nullability */ Option[String],
  eventManipulation: /* unknown nullability */ Option[String],
  eventObjectCatalog: /* unknown nullability */ Option[String],
  eventObjectSchema: /* unknown nullability */ Option[String],
  eventObjectTable: /* unknown nullability */ Option[String],
  actionOrder: /* unknown nullability */ Option[Int],
  actionCondition: /* unknown nullability */ Option[String],
  actionStatement: /* unknown nullability */ Option[String],
  actionOrientation: /* unknown nullability */ Option[String],
  actionTiming: /* unknown nullability */ Option[String],
  actionReferenceOldTable: /* unknown nullability */ Option[String],
  actionReferenceNewTable: /* unknown nullability */ Option[String],
  actionReferenceOldRow: /* unknown nullability */ Option[String],
  actionReferenceNewRow: /* unknown nullability */ Option[String],
  created: /* unknown nullability */ Option[LocalDateTime]
)

object TriggersRow {
  implicit val rowParser: RowParser[TriggersRow] = { row =>
    Success(
      TriggersRow(
        triggerCatalog = row[/* unknown nullability */ Option[String]]("trigger_catalog"),
        triggerSchema = row[/* unknown nullability */ Option[String]]("trigger_schema"),
        triggerName = row[/* unknown nullability */ Option[String]]("trigger_name"),
        eventManipulation = row[/* unknown nullability */ Option[String]]("event_manipulation"),
        eventObjectCatalog = row[/* unknown nullability */ Option[String]]("event_object_catalog"),
        eventObjectSchema = row[/* unknown nullability */ Option[String]]("event_object_schema"),
        eventObjectTable = row[/* unknown nullability */ Option[String]]("event_object_table"),
        actionOrder = row[/* unknown nullability */ Option[Int]]("action_order"),
        actionCondition = row[/* unknown nullability */ Option[String]]("action_condition"),
        actionStatement = row[/* unknown nullability */ Option[String]]("action_statement"),
        actionOrientation = row[/* unknown nullability */ Option[String]]("action_orientation"),
        actionTiming = row[/* unknown nullability */ Option[String]]("action_timing"),
        actionReferenceOldTable = row[/* unknown nullability */ Option[String]]("action_reference_old_table"),
        actionReferenceNewTable = row[/* unknown nullability */ Option[String]]("action_reference_new_table"),
        actionReferenceOldRow = row[/* unknown nullability */ Option[String]]("action_reference_old_row"),
        actionReferenceNewRow = row[/* unknown nullability */ Option[String]]("action_reference_new_row"),
        created = row[/* unknown nullability */ Option[LocalDateTime]]("created")
      )
    )
  }

  implicit val oFormat: OFormat[TriggersRow] = Json.format
}
