package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgShadowRow(
  usename: String,
  usesysid: Long,
  usecreatedb: Boolean,
  usesuper: Boolean,
  userepl: Boolean,
  usebypassrls: Boolean,
  passwd: Option[String],
  valuntil: Option[LocalDateTime],
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
