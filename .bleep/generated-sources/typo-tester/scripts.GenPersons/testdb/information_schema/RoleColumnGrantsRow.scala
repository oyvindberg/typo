package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class RoleColumnGrantsRow(
  /** Points to [[testdb.information_schema.ColumnPrivilegesRow.grantor]] */
  grantor: Option[String],
  /** Points to [[testdb.information_schema.ColumnPrivilegesRow.grantee]] */
  grantee: Option[String],
  /** Points to [[testdb.information_schema.ColumnPrivilegesRow.tableCatalog]] */
  tableCatalog: Option[String],
  /** Points to [[testdb.information_schema.ColumnPrivilegesRow.tableSchema]] */
  tableSchema: Option[String],
  /** Points to [[testdb.information_schema.ColumnPrivilegesRow.tableName]] */
  tableName: Option[String],
  /** Points to [[testdb.information_schema.ColumnPrivilegesRow.columnName]] */
  columnName: Option[String],
  /** Points to [[testdb.information_schema.ColumnPrivilegesRow.privilegeType]] */
  privilegeType: Option[String],
  /** Points to [[testdb.information_schema.ColumnPrivilegesRow.isGrantable]] */
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
