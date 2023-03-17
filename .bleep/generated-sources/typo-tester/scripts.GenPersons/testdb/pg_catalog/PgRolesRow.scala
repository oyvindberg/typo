package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgRolesRow(
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolname]] */
  rolname: String,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolsuper]] */
  rolsuper: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolinherit]] */
  rolinherit: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolcreaterole]] */
  rolcreaterole: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolcreatedb]] */
  rolcreatedb: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolcanlogin]] */
  rolcanlogin: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolreplication]] */
  rolreplication: Boolean,
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolconnlimit]] */
  rolconnlimit: Int,
  rolpassword: /* unknown nullability */ Option[String],
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolvaliduntil]] */
  rolvaliduntil: Option[LocalDateTime],
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolbypassrls]] */
  rolbypassrls: Boolean,
  /** Points to [[testdb.pg_catalog.PgDbRoleSettingRow.setconfig]] */
  rolconfig: Option[Array[String]],
  /** Points to [[testdb.pg_catalog.PgAuthidRow.oid]] */
  oid: Long
)

object PgRolesRow {
  implicit val rowParser: RowParser[PgRolesRow] = { row =>
    Success(
      PgRolesRow(
        rolname = row[String]("rolname"),
        rolsuper = row[Boolean]("rolsuper"),
        rolinherit = row[Boolean]("rolinherit"),
        rolcreaterole = row[Boolean]("rolcreaterole"),
        rolcreatedb = row[Boolean]("rolcreatedb"),
        rolcanlogin = row[Boolean]("rolcanlogin"),
        rolreplication = row[Boolean]("rolreplication"),
        rolconnlimit = row[Int]("rolconnlimit"),
        rolpassword = row[/* unknown nullability */ Option[String]]("rolpassword"),
        rolvaliduntil = row[Option[LocalDateTime]]("rolvaliduntil"),
        rolbypassrls = row[Boolean]("rolbypassrls"),
        rolconfig = row[Option[Array[String]]]("rolconfig"),
        oid = row[Long]("oid")
      )
    )
  }

  implicit val oFormat: OFormat[PgRolesRow] = Json.format
}
