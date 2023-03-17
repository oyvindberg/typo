package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[PgShadowRow] = Json.format
}
