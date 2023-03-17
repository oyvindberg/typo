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

case class ViewColumnUsageRow(
  viewCatalog: /* unknown nullability */ Option[String],
  viewSchema: /* unknown nullability */ Option[String],
  viewName: /* unknown nullability */ Option[String],
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  columnName: /* unknown nullability */ Option[String]
)

object ViewColumnUsageRow {
  implicit val rowParser: RowParser[ViewColumnUsageRow] = { row =>
    Success(
      ViewColumnUsageRow(
        viewCatalog = row[/* unknown nullability */ Option[String]]("view_catalog"),
        viewSchema = row[/* unknown nullability */ Option[String]]("view_schema"),
        viewName = row[/* unknown nullability */ Option[String]]("view_name"),
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        columnName = row[/* unknown nullability */ Option[String]]("column_name")
      )
    )
  }

  implicit val oFormat: OFormat[ViewColumnUsageRow] = new OFormat[ViewColumnUsageRow]{
    override def writes(o: ViewColumnUsageRow): JsObject =
      Json.obj(
        "view_catalog" -> o.viewCatalog,
      "view_schema" -> o.viewSchema,
      "view_name" -> o.viewName,
      "table_catalog" -> o.tableCatalog,
      "table_schema" -> o.tableSchema,
      "table_name" -> o.tableName,
      "column_name" -> o.columnName
      )

    override def reads(json: JsValue): JsResult[ViewColumnUsageRow] = {
      JsResult.fromTry(
        Try(
          ViewColumnUsageRow(
            viewCatalog = json.\("view_catalog").toOption.map(_.as[String]),
            viewSchema = json.\("view_schema").toOption.map(_.as[String]),
            viewName = json.\("view_name").toOption.map(_.as[String]),
            tableCatalog = json.\("table_catalog").toOption.map(_.as[String]),
            tableSchema = json.\("table_schema").toOption.map(_.as[String]),
            tableName = json.\("table_name").toOption.map(_.as[String]),
            columnName = json.\("column_name").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
