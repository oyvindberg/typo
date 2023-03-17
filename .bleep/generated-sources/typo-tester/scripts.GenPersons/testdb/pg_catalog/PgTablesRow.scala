package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgTablesRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  tablename: String,
  tableowner: /* unknown nullability */ Option[String],
  /** Points to [[testdb.pg_catalog.PgTablespaceRow.spcname]] */
  tablespace: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relhasindex]] */
  hasindexes: Boolean,
  /** Points to [[testdb.pg_catalog.PgClassRow.relhasrules]] */
  hasrules: Boolean,
  /** Points to [[testdb.pg_catalog.PgClassRow.relhastriggers]] */
  hastriggers: Boolean,
  /** Points to [[testdb.pg_catalog.PgClassRow.relrowsecurity]] */
  rowsecurity: Boolean
)

object PgTablesRow {
  implicit val rowParser: RowParser[PgTablesRow] = { row =>
    Success(
      PgTablesRow(
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename"),
        tableowner = row[/* unknown nullability */ Option[String]]("tableowner"),
        tablespace = row[String]("tablespace"),
        hasindexes = row[Boolean]("hasindexes"),
        hasrules = row[Boolean]("hasrules"),
        hastriggers = row[Boolean]("hastriggers"),
        rowsecurity = row[Boolean]("rowsecurity")
      )
    )
  }

  implicit val oFormat: OFormat[PgTablesRow] = new OFormat[PgTablesRow]{
    override def writes(o: PgTablesRow): JsObject =
      Json.obj(
        "schemaname" -> o.schemaname,
      "tablename" -> o.tablename,
      "tableowner" -> o.tableowner,
      "tablespace" -> o.tablespace,
      "hasindexes" -> o.hasindexes,
      "hasrules" -> o.hasrules,
      "hastriggers" -> o.hastriggers,
      "rowsecurity" -> o.rowsecurity
      )

    override def reads(json: JsValue): JsResult[PgTablesRow] = {
      JsResult.fromTry(
        Try(
          PgTablesRow(
            schemaname = json.\("schemaname").as[String],
            tablename = json.\("tablename").as[String],
            tableowner = json.\("tableowner").toOption.map(_.as[String]),
            tablespace = json.\("tablespace").as[String],
            hasindexes = json.\("hasindexes").as[Boolean],
            hasrules = json.\("hasrules").as[Boolean],
            hastriggers = json.\("hastriggers").as[Boolean],
            rowsecurity = json.\("rowsecurity").as[Boolean]
          )
        )
      )
    }
  }
}
