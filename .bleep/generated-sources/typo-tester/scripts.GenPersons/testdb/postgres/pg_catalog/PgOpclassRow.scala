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

case class PgOpclassRow(
  oid: Long,
  opcmethod: Long,
  opcname: String,
  opcnamespace: Long,
  opcowner: Long,
  opcfamily: Long,
  opcintype: Long,
  opcdefault: Boolean,
  opckeytype: Long
)

object PgOpclassRow {
  implicit val rowParser: RowParser[PgOpclassRow] = { row =>
    Success(
      PgOpclassRow(
        oid = row[Long]("oid"),
        opcmethod = row[Long]("opcmethod"),
        opcname = row[String]("opcname"),
        opcnamespace = row[Long]("opcnamespace"),
        opcowner = row[Long]("opcowner"),
        opcfamily = row[Long]("opcfamily"),
        opcintype = row[Long]("opcintype"),
        opcdefault = row[Boolean]("opcdefault"),
        opckeytype = row[Long]("opckeytype")
      )
    )
  }

  implicit val oFormat: OFormat[PgOpclassRow] = new OFormat[PgOpclassRow]{
    override def writes(o: PgOpclassRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "opcmethod" -> o.opcmethod,
      "opcname" -> o.opcname,
      "opcnamespace" -> o.opcnamespace,
      "opcowner" -> o.opcowner,
      "opcfamily" -> o.opcfamily,
      "opcintype" -> o.opcintype,
      "opcdefault" -> o.opcdefault,
      "opckeytype" -> o.opckeytype
      )

    override def reads(json: JsValue): JsResult[PgOpclassRow] = {
      JsResult.fromTry(
        Try(
          PgOpclassRow(
            oid = json.\("oid").as[Long],
            opcmethod = json.\("opcmethod").as[Long],
            opcname = json.\("opcname").as[String],
            opcnamespace = json.\("opcnamespace").as[Long],
            opcowner = json.\("opcowner").as[Long],
            opcfamily = json.\("opcfamily").as[Long],
            opcintype = json.\("opcintype").as[Long],
            opcdefault = json.\("opcdefault").as[Boolean],
            opckeytype = json.\("opckeytype").as[Long]
          )
        )
      )
    }
  }
}
