package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class RoleColumnGrantsRow(
  grantor: Option[String],
  grantee: Option[String],
  tableCatalog: Option[String],
  tableSchema: Option[String],
  tableName: Option[String],
  columnName: Option[String],
  privilegeType: Option[String],
  isGrantable: Option[String]
)

object RoleColumnGrantsRow {
  implicit val rowParser: RowParser[RoleColumnGrantsRow] = { row =>
    Success(
      RoleColumnGrantsRow(
        grantor = row[Option[String]]("grantor"),
        grantee = row[Option[String]]("grantee"),
        tableCatalog = row[Option[String]]("table_catalog"),
        tableSchema = row[Option[String]]("table_schema"),
        tableName = row[Option[String]]("table_name"),
        columnName = row[Option[String]]("column_name"),
        privilegeType = row[Option[String]]("privilege_type"),
        isGrantable = row[Option[String]]("is_grantable")
      )
    )
  }

  implicit val oFormat: OFormat[RoleColumnGrantsRow] = Json.format
}
