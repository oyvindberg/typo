package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[ViewsRow] = Json.format
}
