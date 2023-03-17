package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class ViewsRow(
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  viewDefinition: /* unknown nullability */ Option[String],
  checkOption: /* unknown nullability */ Option[String],
  isUpdatable: /* unknown nullability */ Option[String],
  isInsertableInto: /* unknown nullability */ Option[String],
  isTriggerUpdatable: /* unknown nullability */ Option[String],
  isTriggerDeletable: /* unknown nullability */ Option[String],
  isTriggerInsertableInto: /* unknown nullability */ Option[String]
)

object ViewsRow {
  implicit val rowParser: RowParser[ViewsRow] = { row =>
    Success(
      ViewsRow(
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        viewDefinition = row[/* unknown nullability */ Option[String]]("view_definition"),
        checkOption = row[/* unknown nullability */ Option[String]]("check_option"),
        isUpdatable = row[/* unknown nullability */ Option[String]]("is_updatable"),
        isInsertableInto = row[/* unknown nullability */ Option[String]]("is_insertable_into"),
        isTriggerUpdatable = row[/* unknown nullability */ Option[String]]("is_trigger_updatable"),
        isTriggerDeletable = row[/* unknown nullability */ Option[String]]("is_trigger_deletable"),
        isTriggerInsertableInto = row[/* unknown nullability */ Option[String]]("is_trigger_insertable_into")
      )
    )
  }

  implicit val oFormat: OFormat[ViewsRow] = new OFormat[ViewsRow]{
    override def writes(o: ViewsRow): JsObject =
      Json.obj(
        "table_catalog" -> o.tableCatalog,
      "table_schema" -> o.tableSchema,
      "table_name" -> o.tableName,
      "view_definition" -> o.viewDefinition,
      "check_option" -> o.checkOption,
      "is_updatable" -> o.isUpdatable,
      "is_insertable_into" -> o.isInsertableInto,
      "is_trigger_updatable" -> o.isTriggerUpdatable,
      "is_trigger_deletable" -> o.isTriggerDeletable,
      "is_trigger_insertable_into" -> o.isTriggerInsertableInto
      )

    override def reads(json: JsValue): JsResult[ViewsRow] = {
      JsResult.fromTry(
        Try(
          ViewsRow(
            tableCatalog = json.\("table_catalog").toOption.map(_.as[String]),
            tableSchema = json.\("table_schema").toOption.map(_.as[String]),
            tableName = json.\("table_name").toOption.map(_.as[String]),
            viewDefinition = json.\("view_definition").toOption.map(_.as[String]),
            checkOption = json.\("check_option").toOption.map(_.as[String]),
            isUpdatable = json.\("is_updatable").toOption.map(_.as[String]),
            isInsertableInto = json.\("is_insertable_into").toOption.map(_.as[String]),
            isTriggerUpdatable = json.\("is_trigger_updatable").toOption.map(_.as[String]),
            isTriggerDeletable = json.\("is_trigger_deletable").toOption.map(_.as[String]),
            isTriggerInsertableInto = json.\("is_trigger_insertable_into").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
