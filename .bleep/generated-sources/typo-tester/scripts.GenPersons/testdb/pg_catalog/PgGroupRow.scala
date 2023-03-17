package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgGroupRow(
  groname: String,
  grosysid: Long,
  grolist: /* typo doesn't know how to translate: columnType: Array, columnTypeName: _oid, columnClassName: java.sql.Array */ Any
)

object PgGroupRow {
  implicit val rowParser: RowParser[PgGroupRow] = { row =>
    Success(
      PgGroupRow(
        groname = row[String]("groname"),
        grosysid = row[Long]("grosysid"),
        grolist = row[/* typo doesn't know how to translate: columnType: Array, columnTypeName: _oid, columnClassName: java.sql.Array */ Any]("grolist")
      )
    )
  }

  implicit val oFormat: OFormat[PgGroupRow] = Json.format
}
