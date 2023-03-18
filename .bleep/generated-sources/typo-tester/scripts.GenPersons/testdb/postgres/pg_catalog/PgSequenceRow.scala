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

case class PgSequenceRow(
  seqrelid: Long,
  seqtypid: Long,
  seqstart: Long,
  seqincrement: Long,
  seqmax: Long,
  seqmin: Long,
  seqcache: Long,
  seqcycle: Boolean
)

object PgSequenceRow {
  implicit val rowParser: RowParser[PgSequenceRow] = { row =>
    Success(
      PgSequenceRow(
        seqrelid = row[Long]("seqrelid"),
        seqtypid = row[Long]("seqtypid"),
        seqstart = row[Long]("seqstart"),
        seqincrement = row[Long]("seqincrement"),
        seqmax = row[Long]("seqmax"),
        seqmin = row[Long]("seqmin"),
        seqcache = row[Long]("seqcache"),
        seqcycle = row[Boolean]("seqcycle")
      )
    )
  }

  implicit val oFormat: OFormat[PgSequenceRow] = new OFormat[PgSequenceRow]{
    override def writes(o: PgSequenceRow): JsObject =
      Json.obj(
        "seqrelid" -> o.seqrelid,
      "seqtypid" -> o.seqtypid,
      "seqstart" -> o.seqstart,
      "seqincrement" -> o.seqincrement,
      "seqmax" -> o.seqmax,
      "seqmin" -> o.seqmin,
      "seqcache" -> o.seqcache,
      "seqcycle" -> o.seqcycle
      )

    override def reads(json: JsValue): JsResult[PgSequenceRow] = {
      JsResult.fromTry(
        Try(
          PgSequenceRow(
            seqrelid = json.\("seqrelid").as[Long],
            seqtypid = json.\("seqtypid").as[Long],
            seqstart = json.\("seqstart").as[Long],
            seqincrement = json.\("seqincrement").as[Long],
            seqmax = json.\("seqmax").as[Long],
            seqmin = json.\("seqmin").as[Long],
            seqcache = json.\("seqcache").as[Long],
            seqcycle = json.\("seqcycle").as[Boolean]
          )
        )
      )
    }
  }
}
