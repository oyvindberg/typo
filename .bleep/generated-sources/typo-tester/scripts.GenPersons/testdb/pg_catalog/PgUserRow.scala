package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgUserRow(
  /** Points to [[testdb.pg_catalog.PgShadowRow.usename]] */
  usename: Option[String],
  /** Points to [[testdb.pg_catalog.PgShadowRow.usesysid]] */
  usesysid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgShadowRow.usecreatedb]] */
  usecreatedb: Option[Boolean],
  /** Points to [[testdb.pg_catalog.PgShadowRow.usesuper]] */
  usesuper: Option[Boolean],
  /** Points to [[testdb.pg_catalog.PgShadowRow.userepl]] */
  userepl: Option[Boolean],
  /** Points to [[testdb.pg_catalog.PgShadowRow.usebypassrls]] */
  usebypassrls: Option[Boolean],
  passwd: /* unknown nullability */ Option[String],
  /** Points to [[testdb.pg_catalog.PgShadowRow.valuntil]] */
  valuntil: Option[LocalDateTime],
  /** Points to [[testdb.pg_catalog.PgShadowRow.useconfig]] */
  useconfig: Option[Array[String]]
)

object PgUserRow {
  implicit val rowParser: RowParser[PgUserRow] = { row =>
    Success(
      PgUserRow(
        usename = row[Option[String]]("usename"),
        usesysid = row[Option[Long]]("usesysid"),
        usecreatedb = row[Option[Boolean]]("usecreatedb"),
        usesuper = row[Option[Boolean]]("usesuper"),
        userepl = row[Option[Boolean]]("userepl"),
        usebypassrls = row[Option[Boolean]]("usebypassrls"),
        passwd = row[/* unknown nullability */ Option[String]]("passwd"),
        valuntil = row[Option[LocalDateTime]]("valuntil"),
        useconfig = row[Option[Array[String]]]("useconfig")
      )
    )
  }

  implicit val oFormat: OFormat[PgUserRow] = Json.format
}
