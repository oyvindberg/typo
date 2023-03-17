package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class TablesRow(
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  tableType: /* unknown nullability */ Option[String],
  selfReferencingColumnName: /* unknown nullability */ Option[String],
  referenceGeneration: /* unknown nullability */ Option[String],
  userDefinedTypeCatalog: /* unknown nullability */ Option[String],
  userDefinedTypeSchema: /* unknown nullability */ Option[String],
  userDefinedTypeName: /* unknown nullability */ Option[String],
  isInsertableInto: /* unknown nullability */ Option[String],
  isTyped: /* unknown nullability */ Option[String],
  commitAction: /* unknown nullability */ Option[String]
)

object TablesRow {
  implicit val rowParser: RowParser[TablesRow] = { row =>
    Success(
      TablesRow(
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        tableType = row[/* unknown nullability */ Option[String]]("table_type"),
        selfReferencingColumnName = row[/* unknown nullability */ Option[String]]("self_referencing_column_name"),
        referenceGeneration = row[/* unknown nullability */ Option[String]]("reference_generation"),
        userDefinedTypeCatalog = row[/* unknown nullability */ Option[String]]("user_defined_type_catalog"),
        userDefinedTypeSchema = row[/* unknown nullability */ Option[String]]("user_defined_type_schema"),
        userDefinedTypeName = row[/* unknown nullability */ Option[String]]("user_defined_type_name"),
        isInsertableInto = row[/* unknown nullability */ Option[String]]("is_insertable_into"),
        isTyped = row[/* unknown nullability */ Option[String]]("is_typed"),
        commitAction = row[/* unknown nullability */ Option[String]]("commit_action")
      )
    )
  }

  implicit val oFormat: OFormat[TablesRow] = Json.format
}
