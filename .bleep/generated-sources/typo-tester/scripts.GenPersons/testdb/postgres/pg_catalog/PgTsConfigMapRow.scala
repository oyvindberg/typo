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

case class PgTsConfigMapRow(
  mapcfg: Long,
  maptokentype: Int,
  mapseqno: Int,
  mapdict: Long
){
  val compositeId: PgTsConfigMapId = PgTsConfigMapId(mapcfg, maptokentype, mapseqno)
}

object PgTsConfigMapRow {
  implicit val rowParser: RowParser[PgTsConfigMapRow] = { row =>
    Success(
      PgTsConfigMapRow(
        mapcfg = row[Long]("mapcfg"),
        maptokentype = row[Int]("maptokentype"),
        mapseqno = row[Int]("mapseqno"),
        mapdict = row[Long]("mapdict")
      )
    )
  }

  implicit val oFormat: OFormat[PgTsConfigMapRow] = new OFormat[PgTsConfigMapRow]{
    override def writes(o: PgTsConfigMapRow): JsObject =
      Json.obj(
        "mapcfg" -> o.mapcfg,
      "maptokentype" -> o.maptokentype,
      "mapseqno" -> o.mapseqno,
      "mapdict" -> o.mapdict
      )

    override def reads(json: JsValue): JsResult[PgTsConfigMapRow] = {
      JsResult.fromTry(
        Try(
          PgTsConfigMapRow(
            mapcfg = json.\("mapcfg").as[Long],
            maptokentype = json.\("maptokentype").as[Int],
            mapseqno = json.\("mapseqno").as[Int],
            mapdict = json.\("mapdict").as[Long]
          )
        )
      )
    }
  }
}
