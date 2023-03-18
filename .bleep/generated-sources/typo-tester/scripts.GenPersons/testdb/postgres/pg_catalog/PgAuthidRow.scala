package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.ZonedDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgAuthidRow(
  oid: PgAuthidId,
  rolname: String,
  rolsuper: Boolean,
  rolinherit: Boolean,
  rolcreaterole: Boolean,
  rolcreatedb: Boolean,
  rolcanlogin: Boolean,
  rolreplication: Boolean,
  rolbypassrls: Boolean,
  rolconnlimit: Int,
  rolpassword: Option[String],
  rolvaliduntil: Option[ZonedDateTime]
)

object PgAuthidRow {
  implicit val rowParser: RowParser[PgAuthidRow] = { row =>
    Success(
      PgAuthidRow(
        oid = row[PgAuthidId]("oid"),
        rolname = row[String]("rolname"),
        rolsuper = row[Boolean]("rolsuper"),
        rolinherit = row[Boolean]("rolinherit"),
        rolcreaterole = row[Boolean]("rolcreaterole"),
        rolcreatedb = row[Boolean]("rolcreatedb"),
        rolcanlogin = row[Boolean]("rolcanlogin"),
        rolreplication = row[Boolean]("rolreplication"),
        rolbypassrls = row[Boolean]("rolbypassrls"),
        rolconnlimit = row[Int]("rolconnlimit"),
        rolpassword = row[Option[String]]("rolpassword"),
        rolvaliduntil = row[Option[ZonedDateTime]]("rolvaliduntil")
      )
    )
  }

  implicit val oFormat: OFormat[PgAuthidRow] = new OFormat[PgAuthidRow]{
    override def writes(o: PgAuthidRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "rolname" -> o.rolname,
      "rolsuper" -> o.rolsuper,
      "rolinherit" -> o.rolinherit,
      "rolcreaterole" -> o.rolcreaterole,
      "rolcreatedb" -> o.rolcreatedb,
      "rolcanlogin" -> o.rolcanlogin,
      "rolreplication" -> o.rolreplication,
      "rolbypassrls" -> o.rolbypassrls,
      "rolconnlimit" -> o.rolconnlimit,
      "rolpassword" -> o.rolpassword,
      "rolvaliduntil" -> o.rolvaliduntil
      )

    override def reads(json: JsValue): JsResult[PgAuthidRow] = {
      JsResult.fromTry(
        Try(
          PgAuthidRow(
            oid = json.\("oid").as[PgAuthidId],
            rolname = json.\("rolname").as[String],
            rolsuper = json.\("rolsuper").as[Boolean],
            rolinherit = json.\("rolinherit").as[Boolean],
            rolcreaterole = json.\("rolcreaterole").as[Boolean],
            rolcreatedb = json.\("rolcreatedb").as[Boolean],
            rolcanlogin = json.\("rolcanlogin").as[Boolean],
            rolreplication = json.\("rolreplication").as[Boolean],
            rolbypassrls = json.\("rolbypassrls").as[Boolean],
            rolconnlimit = json.\("rolconnlimit").as[Int],
            rolpassword = json.\("rolpassword").toOption.map(_.as[String]),
            rolvaliduntil = json.\("rolvaliduntil").toOption.map(_.as[ZonedDateTime])
          )
        )
      )
    }
  }
}
