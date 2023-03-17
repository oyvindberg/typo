package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgRolesRow(
  rolname: String,
  rolsuper: Boolean,
  rolinherit: Boolean,
  rolcreaterole: Boolean,
  rolcreatedb: Boolean,
  rolcanlogin: Boolean,
  rolreplication: Boolean,
  rolconnlimit: Int,
  rolpassword: /* unknown nullability */ Option[String],
  rolvaliduntil: Option[LocalDateTime],
  rolbypassrls: Boolean,
  rolconfig: Option[Array[String]],
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
