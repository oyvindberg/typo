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

case class PgInitPrivsRow(
  objoid: Long,
  classoid: Long,
  objsubid: Int,
  privtype: String,
  initprivs: Array[String]
){
  val objoidAndClassoidAndObjsubid: PgInitPrivsId = PgInitPrivsId(objoid, classoid, objsubid)
}

object PgInitPrivsRow {
  implicit val rowParser: RowParser[PgInitPrivsRow] = { row =>
    Success(
      PgInitPrivsRow(
        objoid = row[Long]("objoid"),
        classoid = row[Long]("classoid"),
        objsubid = row[Int]("objsubid"),
        privtype = row[String]("privtype"),
        initprivs = row[Array[String]]("initprivs")
      )
    )
  }

  implicit val oFormat: OFormat[PgInitPrivsRow] = new OFormat[PgInitPrivsRow]{
    override def writes(o: PgInitPrivsRow): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
      "classoid" -> o.classoid,
      "objsubid" -> o.objsubid,
      "privtype" -> o.privtype,
      "initprivs" -> o.initprivs
      )

    override def reads(json: JsValue): JsResult[PgInitPrivsRow] = {
      JsResult.fromTry(
        Try(
          PgInitPrivsRow(
            objoid = json.\("objoid").as[Long],
            classoid = json.\("classoid").as[Long],
            objsubid = json.\("objsubid").as[Int],
            privtype = json.\("privtype").as[String],
            initprivs = json.\("initprivs").as[Array[String]]
          )
        )
      )
    }
  }
}
