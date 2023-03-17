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

case class TableConstraintsRow(
  constraintCatalog: /* unknown nullability */ Option[String],
  constraintSchema: /* unknown nullability */ Option[String],
  constraintName: /* unknown nullability */ Option[String],
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  constraintType: /* unknown nullability */ Option[String],
  isDeferrable: /* unknown nullability */ Option[String],
  initiallyDeferred: /* unknown nullability */ Option[String],
  enforced: /* unknown nullability */ Option[String]
)

object TableConstraintsRow {
  implicit val rowParser: RowParser[TableConstraintsRow] = { row =>
    Success(
      TableConstraintsRow(
        constraintCatalog = row[/* unknown nullability */ Option[String]]("constraint_catalog"),
        constraintSchema = row[/* unknown nullability */ Option[String]]("constraint_schema"),
        constraintName = row[/* unknown nullability */ Option[String]]("constraint_name"),
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        constraintType = row[/* unknown nullability */ Option[String]]("constraint_type"),
        isDeferrable = row[/* unknown nullability */ Option[String]]("is_deferrable"),
        initiallyDeferred = row[/* unknown nullability */ Option[String]]("initially_deferred"),
        enforced = row[/* unknown nullability */ Option[String]]("enforced")
      )
    )
  }

  implicit val oFormat: OFormat[TableConstraintsRow] = new OFormat[TableConstraintsRow]{
    override def writes(o: TableConstraintsRow): JsObject =
      Json.obj(
        "constraint_catalog" -> o.constraintCatalog,
      "constraint_schema" -> o.constraintSchema,
      "constraint_name" -> o.constraintName,
      "table_catalog" -> o.tableCatalog,
      "table_schema" -> o.tableSchema,
      "table_name" -> o.tableName,
      "constraint_type" -> o.constraintType,
      "is_deferrable" -> o.isDeferrable,
      "initially_deferred" -> o.initiallyDeferred,
      "enforced" -> o.enforced
      )

    override def reads(json: JsValue): JsResult[TableConstraintsRow] = {
      JsResult.fromTry(
        Try(
          TableConstraintsRow(
            constraintCatalog = json.\("constraint_catalog").toOption.map(_.as[String]),
            constraintSchema = json.\("constraint_schema").toOption.map(_.as[String]),
            constraintName = json.\("constraint_name").toOption.map(_.as[String]),
            tableCatalog = json.\("table_catalog").toOption.map(_.as[String]),
            tableSchema = json.\("table_schema").toOption.map(_.as[String]),
            tableName = json.\("table_name").toOption.map(_.as[String]),
            constraintType = json.\("constraint_type").toOption.map(_.as[String]),
            isDeferrable = json.\("is_deferrable").toOption.map(_.as[String]),
            initiallyDeferred = json.\("initially_deferred").toOption.map(_.as[String]),
            enforced = json.\("enforced").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
