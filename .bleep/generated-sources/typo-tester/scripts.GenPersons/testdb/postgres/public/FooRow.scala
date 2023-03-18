package testdb
package postgres
package public

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try
import testdb.postgres.information_schema.TableConstraintsRow

case class FooRow(
  /** Points to [[TableConstraintsRow.constraintCatalog]] */
  constraintCatalog: Option[String],
  /** Points to [[TableConstraintsRow.constraintSchema]] */
  constraintSchema: Option[String],
  /** Points to [[TableConstraintsRow.constraintName]] */
  constraintName: Option[String],
  /** Points to [[TableConstraintsRow.tableCatalog]] */
  tableCatalog: Option[String],
  /** Points to [[TableConstraintsRow.tableSchema]] */
  tableSchema: Option[String],
  /** Points to [[TableConstraintsRow.tableName]] */
  tableName: Option[String],
  /** Points to [[TableConstraintsRow.constraintType]] */
  constraintType: Option[String],
  /** Points to [[TableConstraintsRow.isDeferrable]] */
  isDeferrable: Option[String],
  /** Points to [[TableConstraintsRow.initiallyDeferred]] */
  initiallyDeferred: Option[String],
  /** Points to [[TableConstraintsRow.enforced]] */
  enforced: Option[String]
)

object FooRow {
  implicit val rowParser: RowParser[FooRow] = { row =>
    Success(
      FooRow(
        constraintCatalog = row[Option[String]]("constraint_catalog"),
        constraintSchema = row[Option[String]]("constraint_schema"),
        constraintName = row[Option[String]]("constraint_name"),
        tableCatalog = row[Option[String]]("table_catalog"),
        tableSchema = row[Option[String]]("table_schema"),
        tableName = row[Option[String]]("table_name"),
        constraintType = row[Option[String]]("constraint_type"),
        isDeferrable = row[Option[String]]("is_deferrable"),
        initiallyDeferred = row[Option[String]]("initially_deferred"),
        enforced = row[Option[String]]("enforced")
      )
    )
  }

  implicit val oFormat: OFormat[FooRow] = new OFormat[FooRow]{
    override def writes(o: FooRow): JsObject =
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

    override def reads(json: JsValue): JsResult[FooRow] = {
      JsResult.fromTry(
        Try(
          FooRow(
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
