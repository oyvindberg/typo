package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgTsTemplateRowUnsaved(
  tmplname: String,
  tmplnamespace: Long,
  tmplinit: String,
  tmpllexize: String
)
object PgTsTemplateRowUnsaved {
  implicit val oFormat: OFormat[PgTsTemplateRowUnsaved] = new OFormat[PgTsTemplateRowUnsaved]{
    override def writes(o: PgTsTemplateRowUnsaved): JsObject =
      Json.obj(
        "tmplname" -> o.tmplname,
      "tmplnamespace" -> o.tmplnamespace,
      "tmplinit" -> o.tmplinit,
      "tmpllexize" -> o.tmpllexize
      )

    override def reads(json: JsValue): JsResult[PgTsTemplateRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgTsTemplateRowUnsaved(
            tmplname = json.\("tmplname").as[String],
            tmplnamespace = json.\("tmplnamespace").as[Long],
            tmplinit = json.\("tmplinit").as[String],
            tmpllexize = json.\("tmpllexize").as[String]
          )
        )
      )
    }
  }
}
