package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class ColumnPrivilegesRow(
  grantor: /* unknown nullability */ Option[String],
  grantee: /* unknown nullability */ Option[String],
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  columnName: /* unknown nullability */ Option[String],
  privilegeType: /* unknown nullability */ Option[String],
  isGrantable: /* unknown nullability */ Option[String]
)

object ColumnPrivilegesRow {
  implicit val rowParser: RowParser[ColumnPrivilegesRow] = { row =>
    Success(
      ColumnPrivilegesRow(
        grantor = row[/* unknown nullability */ Option[String]]("grantor"),
        grantee = row[/* unknown nullability */ Option[String]]("grantee"),
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        columnName = row[/* unknown nullability */ Option[String]]("column_name"),
        privilegeType = row[/* unknown nullability */ Option[String]]("privilege_type"),
        isGrantable = row[/* unknown nullability */ Option[String]]("is_grantable")
      )
    )
  }

  implicit val oFormat: OFormat[ColumnPrivilegesRow] = new OFormat[ColumnPrivilegesRow]{
    override def writes(o: ColumnPrivilegesRow): JsObject =
      Json.obj(
        "grantor" -> o.grantor,
      "grantee" -> o.grantee,
      "table_catalog" -> o.tableCatalog,
      "table_schema" -> o.tableSchema,
      "table_name" -> o.tableName,
      "column_name" -> o.columnName,
      "privilege_type" -> o.privilegeType,
      "is_grantable" -> o.isGrantable
      )

    override def reads(json: JsValue): JsResult[ColumnPrivilegesRow] = {
      JsResult.fromTry(
        Try(
          ColumnPrivilegesRow(
            grantor = json.\("grantor").toOption.map(_.as[String]),
            grantee = json.\("grantee").toOption.map(_.as[String]),
            tableCatalog = json.\("table_catalog").toOption.map(_.as[String]),
            tableSchema = json.\("table_schema").toOption.map(_.as[String]),
            tableName = json.\("table_name").toOption.map(_.as[String]),
            columnName = json.\("column_name").toOption.map(_.as[String]),
            privilegeType = json.\("privilege_type").toOption.map(_.as[String]),
            isGrantable = json.\("is_grantable").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
