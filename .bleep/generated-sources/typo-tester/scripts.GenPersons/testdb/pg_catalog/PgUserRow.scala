package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgUserRow(
  usename: Option[String],
  usesysid: Option[Long],
  usecreatedb: Option[Boolean],
  usesuper: Option[Boolean],
  userepl: Option[Boolean],
  usebypassrls: Option[Boolean],
  passwd: /* unknown nullability */ Option[String],
  valuntil: Option[LocalDateTime],
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
