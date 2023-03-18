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

case class PgAuthMembersRow(
  roleid: Long,
  member: Long,
  grantor: Long,
  adminOption: Boolean
){
  val compositeId: PgAuthMembersId = PgAuthMembersId(roleid, member)
}

object PgAuthMembersRow {
  implicit val rowParser: RowParser[PgAuthMembersRow] = { row =>
    Success(
      PgAuthMembersRow(
        roleid = row[Long]("roleid"),
        member = row[Long]("member"),
        grantor = row[Long]("grantor"),
        adminOption = row[Boolean]("admin_option")
      )
    )
  }

  implicit val oFormat: OFormat[PgAuthMembersRow] = new OFormat[PgAuthMembersRow]{
    override def writes(o: PgAuthMembersRow): JsObject =
      Json.obj(
        "roleid" -> o.roleid,
      "member" -> o.member,
      "grantor" -> o.grantor,
      "admin_option" -> o.adminOption
      )

    override def reads(json: JsValue): JsResult[PgAuthMembersRow] = {
      JsResult.fromTry(
        Try(
          PgAuthMembersRow(
            roleid = json.\("roleid").as[Long],
            member = json.\("member").as[Long],
            grantor = json.\("grantor").as[Long],
            adminOption = json.\("admin_option").as[Boolean]
          )
        )
      )
    }
  }
}
