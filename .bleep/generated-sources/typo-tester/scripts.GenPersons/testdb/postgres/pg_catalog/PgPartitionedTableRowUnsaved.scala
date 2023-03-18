package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgPartitionedTableRowUnsaved(
  partstrat: String,
  partnatts: Short,
  partdefid: Long,
  partattrs: String,
  partclass: String,
  partcollation: String,
  partexprs: Option[String]
)
object PgPartitionedTableRowUnsaved {
  implicit val oFormat: OFormat[PgPartitionedTableRowUnsaved] = new OFormat[PgPartitionedTableRowUnsaved]{
    override def writes(o: PgPartitionedTableRowUnsaved): JsObject =
      Json.obj(
        "partstrat" -> o.partstrat,
      "partnatts" -> o.partnatts,
      "partdefid" -> o.partdefid,
      "partattrs" -> o.partattrs,
      "partclass" -> o.partclass,
      "partcollation" -> o.partcollation,
      "partexprs" -> o.partexprs
      )

    override def reads(json: JsValue): JsResult[PgPartitionedTableRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgPartitionedTableRowUnsaved(
            partstrat = json.\("partstrat").as[String],
            partnatts = json.\("partnatts").as[Short],
            partdefid = json.\("partdefid").as[Long],
            partattrs = json.\("partattrs").as[String],
            partclass = json.\("partclass").as[String],
            partcollation = json.\("partcollation").as[String],
            partexprs = json.\("partexprs").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
