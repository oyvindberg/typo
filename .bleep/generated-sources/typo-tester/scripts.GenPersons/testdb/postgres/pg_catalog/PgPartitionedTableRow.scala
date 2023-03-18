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

case class PgPartitionedTableRow(
  partrelid: Long,
  partstrat: String,
  partnatts: Short,
  partdefid: Long,
  partattrs: String,
  partclass: String,
  partcollation: String,
  partexprs: Option[String]
)

object PgPartitionedTableRow {
  implicit val rowParser: RowParser[PgPartitionedTableRow] = { row =>
    Success(
      PgPartitionedTableRow(
        partrelid = row[Long]("partrelid"),
        partstrat = row[String]("partstrat"),
        partnatts = row[Short]("partnatts"),
        partdefid = row[Long]("partdefid"),
        partattrs = row[String]("partattrs"),
        partclass = row[String]("partclass"),
        partcollation = row[String]("partcollation"),
        partexprs = row[Option[String]]("partexprs")
      )
    )
  }

  implicit val oFormat: OFormat[PgPartitionedTableRow] = new OFormat[PgPartitionedTableRow]{
    override def writes(o: PgPartitionedTableRow): JsObject =
      Json.obj(
        "partrelid" -> o.partrelid,
      "partstrat" -> o.partstrat,
      "partnatts" -> o.partnatts,
      "partdefid" -> o.partdefid,
      "partattrs" -> o.partattrs,
      "partclass" -> o.partclass,
      "partcollation" -> o.partcollation,
      "partexprs" -> o.partexprs
      )

    override def reads(json: JsValue): JsResult[PgPartitionedTableRow] = {
      JsResult.fromTry(
        Try(
          PgPartitionedTableRow(
            partrelid = json.\("partrelid").as[Long],
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
