package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgShadowRow(
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolname]] */
  usename: String,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.oid]] */
  usesysid: Long,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolcreatedb]] */
  usecreatedb: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolsuper]] */
  usesuper: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolreplication]] */
  userepl: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolbypassrls]] */
  usebypassrls: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolpassword]] */
  passwd: Option[String],
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolvaliduntil]] */
  valuntil: Option[LocalDateTime],
  /** Points to [[testdb.pg_catalog.PgDbRoleSettingRow.setconfig]] */
  useconfig: Option[Array[String]]
)

object PgShadowRow {
  implicit val rowParser: RowParser[PgShadowRow] = { row =>
    Success(
      PgShadowRow(
        usename = row[String]("usename"),
        usesysid = row[Long]("usesysid"),
        usecreatedb = row[Boolean]("usecreatedb"),
        usesuper = row[Boolean]("usesuper"),
        userepl = row[Boolean]("userepl"),
        usebypassrls = row[Boolean]("usebypassrls"),
        passwd = row[Option[String]]("passwd"),
        valuntil = row[Option[LocalDateTime]]("valuntil"),
        useconfig = row[Option[Array[String]]]("useconfig")
      )
    )
  }

  implicit val oFormat: OFormat[PgShadowRow] = new OFormat[PgShadowRow]{
    override def writes(o: PgShadowRow): JsObject =
      Json.obj(
        "usename" -> o.usename,
      "usesysid" -> o.usesysid,
      "usecreatedb" -> o.usecreatedb,
      "usesuper" -> o.usesuper,
      "userepl" -> o.userepl,
      "usebypassrls" -> o.usebypassrls,
      "passwd" -> o.passwd,
      "valuntil" -> o.valuntil,
      "useconfig" -> o.useconfig
      )

    override def reads(json: JsValue): JsResult[PgShadowRow] = {
      JsResult.fromTry(
        Try(
          PgShadowRow(
            usename = json.\("usename").as[String],
            usesysid = json.\("usesysid").as[Long],
            usecreatedb = json.\("usecreatedb").as[Boolean],
            usesuper = json.\("usesuper").as[Boolean],
            userepl = json.\("userepl").as[Boolean],
            usebypassrls = json.\("usebypassrls").as[Boolean],
            passwd = json.\("passwd").toOption.map(_.as[String]),
            valuntil = json.\("valuntil").toOption.map(_.as[LocalDateTime]),
            useconfig = json.\("useconfig").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
