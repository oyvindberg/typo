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

case class ForeignTableOptionsRow(
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableCatalog]] */
  foreignTableCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableSchema]] */
  foreignTableSchema: Option[String],
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableName]] */
  foreignTableName: Option[String],
  optionName: /* unknown nullability */ Option[String],
  optionValue: /* unknown nullability */ Option[String]
)

object ForeignTableOptionsRow {
  implicit val rowParser: RowParser[ForeignTableOptionsRow] = { row =>
    Success(
      ForeignTableOptionsRow(
        foreignTableCatalog = row[Option[String]]("foreign_table_catalog"),
        foreignTableSchema = row[Option[String]]("foreign_table_schema"),
        foreignTableName = row[Option[String]]("foreign_table_name"),
        optionName = row[/* unknown nullability */ Option[String]]("option_name"),
        optionValue = row[/* unknown nullability */ Option[String]]("option_value")
      )
    )
  }

  implicit val oFormat: OFormat[ForeignTableOptionsRow] = new OFormat[ForeignTableOptionsRow]{
    override def writes(o: ForeignTableOptionsRow): JsObject =
      Json.obj(
        "foreign_table_catalog" -> o.foreignTableCatalog,
      "foreign_table_schema" -> o.foreignTableSchema,
      "foreign_table_name" -> o.foreignTableName,
      "option_name" -> o.optionName,
      "option_value" -> o.optionValue
      )

    override def reads(json: JsValue): JsResult[ForeignTableOptionsRow] = {
      JsResult.fromTry(
        Try(
          ForeignTableOptionsRow(
            foreignTableCatalog = json.\("foreign_table_catalog").toOption.map(_.as[String]),
            foreignTableSchema = json.\("foreign_table_schema").toOption.map(_.as[String]),
            foreignTableName = json.\("foreign_table_name").toOption.map(_.as[String]),
            optionName = json.\("option_name").toOption.map(_.as[String]),
            optionValue = json.\("option_value").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
