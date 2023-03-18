package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgDbRoleSettingRow(
  setdatabase: Long,
  setrole: Long,
  setconfig: Option[Array[String]]
)

object PgDbRoleSettingRow {
  implicit val rowParser: RowParser[PgDbRoleSettingRow] = { row =>
    Success(
      PgDbRoleSettingRow(
        setdatabase = row[Long]("setdatabase"),
        setrole = row[Long]("setrole"),
        setconfig = row[Option[Array[String]]]("setconfig")
      )
    )
  }

  implicit val oFormat: OFormat[PgDbRoleSettingRow] = new OFormat[PgDbRoleSettingRow]{
    override def writes(o: PgDbRoleSettingRow): JsObject =
      Json.obj(
        "setdatabase" -> o.setdatabase,
      "setrole" -> o.setrole,
      "setconfig" -> o.setconfig
      )

    override def reads(json: JsValue): JsResult[PgDbRoleSettingRow] = {
      JsResult.fromTry(
        Try(
          PgDbRoleSettingRow(
            setdatabase = json.\("setdatabase").as[Long],
            setrole = json.\("setrole").as[Long],
            setconfig = json.\("setconfig").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
