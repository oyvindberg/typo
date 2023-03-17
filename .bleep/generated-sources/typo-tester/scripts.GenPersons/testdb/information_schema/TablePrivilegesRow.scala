package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class TablePrivilegesRow(
  grantor: /* unknown nullability */ Option[String],
  grantee: /* unknown nullability */ Option[String],
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  privilegeType: /* unknown nullability */ Option[String],
  isGrantable: /* unknown nullability */ Option[String],
  withHierarchy: /* unknown nullability */ Option[String]
)

object TablePrivilegesRow {
  implicit val rowParser: RowParser[TablePrivilegesRow] = { row =>
    Success(
      TablePrivilegesRow(
        grantor = row[/* unknown nullability */ Option[String]]("grantor"),
        grantee = row[/* unknown nullability */ Option[String]]("grantee"),
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        privilegeType = row[/* unknown nullability */ Option[String]]("privilege_type"),
        isGrantable = row[/* unknown nullability */ Option[String]]("is_grantable"),
        withHierarchy = row[/* unknown nullability */ Option[String]]("with_hierarchy")
      )
    )
  }

  implicit val oFormat: OFormat[TablePrivilegesRow] = Json.format
}
