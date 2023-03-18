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

case class PgOpfamilyRow(
  oid: Long,
  opfmethod: Long,
  opfname: String,
  opfnamespace: Long,
  opfowner: Long
)

object PgOpfamilyRow {
  implicit val rowParser: RowParser[PgOpfamilyRow] = { row =>
    Success(
      PgOpfamilyRow(
        oid = row[Long]("oid"),
        opfmethod = row[Long]("opfmethod"),
        opfname = row[String]("opfname"),
        opfnamespace = row[Long]("opfnamespace"),
        opfowner = row[Long]("opfowner")
      )
    )
  }

  implicit val oFormat: OFormat[PgOpfamilyRow] = new OFormat[PgOpfamilyRow]{
    override def writes(o: PgOpfamilyRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "opfmethod" -> o.opfmethod,
      "opfname" -> o.opfname,
      "opfnamespace" -> o.opfnamespace,
      "opfowner" -> o.opfowner
      )

    override def reads(json: JsValue): JsResult[PgOpfamilyRow] = {
      JsResult.fromTry(
        Try(
          PgOpfamilyRow(
            oid = json.\("oid").as[Long],
            opfmethod = json.\("opfmethod").as[Long],
            opfname = json.\("opfname").as[String],
            opfnamespace = json.\("opfnamespace").as[Long],
            opfowner = json.\("opfowner").as[Long]
          )
        )
      )
    }
  }
}
