package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class TriggeredUpdateColumnsRow(
  triggerCatalog: /* unknown nullability */ Option[String],
  triggerSchema: /* unknown nullability */ Option[String],
  triggerName: /* unknown nullability */ Option[String],
  eventObjectCatalog: /* unknown nullability */ Option[String],
  eventObjectSchema: /* unknown nullability */ Option[String],
  eventObjectTable: /* unknown nullability */ Option[String],
  eventObjectColumn: /* unknown nullability */ Option[String]
)

object TriggeredUpdateColumnsRow {
  implicit val rowParser: RowParser[TriggeredUpdateColumnsRow] = { row =>
    Success(
      TriggeredUpdateColumnsRow(
        triggerCatalog = row[/* unknown nullability */ Option[String]]("trigger_catalog"),
        triggerSchema = row[/* unknown nullability */ Option[String]]("trigger_schema"),
        triggerName = row[/* unknown nullability */ Option[String]]("trigger_name"),
        eventObjectCatalog = row[/* unknown nullability */ Option[String]]("event_object_catalog"),
        eventObjectSchema = row[/* unknown nullability */ Option[String]]("event_object_schema"),
        eventObjectTable = row[/* unknown nullability */ Option[String]]("event_object_table"),
        eventObjectColumn = row[/* unknown nullability */ Option[String]]("event_object_column")
      )
    )
  }

  implicit val oFormat: OFormat[TriggeredUpdateColumnsRow] = new OFormat[TriggeredUpdateColumnsRow]{
    override def writes(o: TriggeredUpdateColumnsRow): JsObject =
      Json.obj(
        "trigger_catalog" -> o.triggerCatalog,
      "trigger_schema" -> o.triggerSchema,
      "trigger_name" -> o.triggerName,
      "event_object_catalog" -> o.eventObjectCatalog,
      "event_object_schema" -> o.eventObjectSchema,
      "event_object_table" -> o.eventObjectTable,
      "event_object_column" -> o.eventObjectColumn
      )

    override def reads(json: JsValue): JsResult[TriggeredUpdateColumnsRow] = {
      JsResult.fromTry(
        Try(
          TriggeredUpdateColumnsRow(
            triggerCatalog = json.\("trigger_catalog").toOption.map(_.as[String]),
            triggerSchema = json.\("trigger_schema").toOption.map(_.as[String]),
            triggerName = json.\("trigger_name").toOption.map(_.as[String]),
            eventObjectCatalog = json.\("event_object_catalog").toOption.map(_.as[String]),
            eventObjectSchema = json.\("event_object_schema").toOption.map(_.as[String]),
            eventObjectTable = json.\("event_object_table").toOption.map(_.as[String]),
            eventObjectColumn = json.\("event_object_column").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
