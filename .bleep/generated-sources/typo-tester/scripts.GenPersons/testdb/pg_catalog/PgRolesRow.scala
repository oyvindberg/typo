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

  implicit val oFormat: OFormat[PgRolesRow] = new OFormat[PgRolesRow]{
    override def writes(o: PgRolesRow): JsObject =
      Json.obj(
        "rolname" -> o.rolname,
      "rolsuper" -> o.rolsuper,
      "rolinherit" -> o.rolinherit,
      "rolcreaterole" -> o.rolcreaterole,
      "rolcreatedb" -> o.rolcreatedb,
      "rolcanlogin" -> o.rolcanlogin,
      "rolreplication" -> o.rolreplication,
      "rolconnlimit" -> o.rolconnlimit,
      "rolpassword" -> o.rolpassword,
      "rolvaliduntil" -> o.rolvaliduntil,
      "rolbypassrls" -> o.rolbypassrls,
      "rolconfig" -> o.rolconfig,
      "oid" -> o.oid
      )

    override def reads(json: JsValue): JsResult[PgRolesRow] = {
      JsResult.fromTry(
        Try(
          PgRolesRow(
            rolname = json.\("rolname").as[String],
            rolsuper = json.\("rolsuper").as[Boolean],
            rolinherit = json.\("rolinherit").as[Boolean],
            rolcreaterole = json.\("rolcreaterole").as[Boolean],
            rolcreatedb = json.\("rolcreatedb").as[Boolean],
            rolcanlogin = json.\("rolcanlogin").as[Boolean],
            rolreplication = json.\("rolreplication").as[Boolean],
            rolconnlimit = json.\("rolconnlimit").as[Int],
            rolpassword = json.\("rolpassword").toOption.map(_.as[String]),
            rolvaliduntil = json.\("rolvaliduntil").toOption.map(_.as[LocalDateTime]),
            rolbypassrls = json.\("rolbypassrls").as[Boolean],
            rolconfig = json.\("rolconfig").toOption.map(_.as[Array[String]]),
            oid = json.\("oid").as[Long]
          )
        )
      )
    }
  }
}
