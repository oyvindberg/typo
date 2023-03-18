package testdb
package postgres
package information_schema

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

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

  implicit val oFormat: OFormat[TriggersRow] = new OFormat[TriggersRow]{
    override def writes(o: TriggersRow): JsObject =
      Json.obj(
        "trigger_catalog" -> o.triggerCatalog,
      "trigger_schema" -> o.triggerSchema,
      "trigger_name" -> o.triggerName,
      "event_manipulation" -> o.eventManipulation,
      "event_object_catalog" -> o.eventObjectCatalog,
      "event_object_schema" -> o.eventObjectSchema,
      "event_object_table" -> o.eventObjectTable,
      "action_order" -> o.actionOrder,
      "action_condition" -> o.actionCondition,
      "action_statement" -> o.actionStatement,
      "action_orientation" -> o.actionOrientation,
      "action_timing" -> o.actionTiming,
      "action_reference_old_table" -> o.actionReferenceOldTable,
      "action_reference_new_table" -> o.actionReferenceNewTable,
      "action_reference_old_row" -> o.actionReferenceOldRow,
      "action_reference_new_row" -> o.actionReferenceNewRow,
      "created" -> o.created
      )

    override def reads(json: JsValue): JsResult[TriggersRow] = {
      JsResult.fromTry(
        Try(
          TriggersRow(
            triggerCatalog = json.\("trigger_catalog").toOption.map(_.as[String]),
            triggerSchema = json.\("trigger_schema").toOption.map(_.as[String]),
            triggerName = json.\("trigger_name").toOption.map(_.as[String]),
            eventManipulation = json.\("event_manipulation").toOption.map(_.as[String]),
            eventObjectCatalog = json.\("event_object_catalog").toOption.map(_.as[String]),
            eventObjectSchema = json.\("event_object_schema").toOption.map(_.as[String]),
            eventObjectTable = json.\("event_object_table").toOption.map(_.as[String]),
            actionOrder = json.\("action_order").toOption.map(_.as[Int]),
            actionCondition = json.\("action_condition").toOption.map(_.as[String]),
            actionStatement = json.\("action_statement").toOption.map(_.as[String]),
            actionOrientation = json.\("action_orientation").toOption.map(_.as[String]),
            actionTiming = json.\("action_timing").toOption.map(_.as[String]),
            actionReferenceOldTable = json.\("action_reference_old_table").toOption.map(_.as[String]),
            actionReferenceNewTable = json.\("action_reference_new_table").toOption.map(_.as[String]),
            actionReferenceOldRow = json.\("action_reference_old_row").toOption.map(_.as[String]),
            actionReferenceNewRow = json.\("action_reference_new_row").toOption.map(_.as[String]),
            created = json.\("created").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}
