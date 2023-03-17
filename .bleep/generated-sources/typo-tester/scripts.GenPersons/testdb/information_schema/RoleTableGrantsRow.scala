package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class RoleTableGrantsRow(
  /** Points to [[testdb.information_schema.TablePrivilegesRow.grantor]] */
  grantor: Option[String],
  /** Points to [[testdb.information_schema.TablePrivilegesRow.grantee]] */
  grantee: Option[String],
  /** Points to [[testdb.information_schema.TablePrivilegesRow.tableCatalog]] */
  tableCatalog: Option[String],
  /** Points to [[testdb.information_schema.TablePrivilegesRow.tableSchema]] */
  tableSchema: Option[String],
  /** Points to [[testdb.information_schema.TablePrivilegesRow.tableName]] */
  tableName: Option[String],
  /** Points to [[testdb.information_schema.TablePrivilegesRow.privilegeType]] */
  privilegeType: Option[String],
  /** Points to [[testdb.information_schema.TablePrivilegesRow.isGrantable]] */
  isGrantable: Option[String],
  /** Points to [[testdb.information_schema.TablePrivilegesRow.withHierarchy]] */
  withHierarchy: Option[String]
)

object RoleTableGrantsRow {
  implicit val rowParser: RowParser[RoleTableGrantsRow] = { row =>
    Success(
      RoleTableGrantsRow(
        grantor = row[Option[String]]("grantor"),
        grantee = row[Option[String]]("grantee"),
        tableCatalog = row[Option[String]]("table_catalog"),
        tableSchema = row[Option[String]]("table_schema"),
        tableName = row[Option[String]]("table_name"),
        privilegeType = row[Option[String]]("privilege_type"),
        isGrantable = row[Option[String]]("is_grantable"),
        withHierarchy = row[Option[String]]("with_hierarchy")
      )
    )
  }

  implicit val oFormat: OFormat[RoleTableGrantsRow] = Json.format
}
