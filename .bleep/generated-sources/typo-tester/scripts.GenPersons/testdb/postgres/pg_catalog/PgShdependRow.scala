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

case class PgShdependRow(
  dbid: Long,
  classid: Long,
  objid: Long,
  objsubid: Int,
  refclassid: Long,
  refobjid: Long,
  deptype: String
)

object PgShdependRow {
  implicit val rowParser: RowParser[PgShdependRow] = { row =>
    Success(
      PgShdependRow(
        dbid = row[Long]("dbid"),
        classid = row[Long]("classid"),
        objid = row[Long]("objid"),
        objsubid = row[Int]("objsubid"),
        refclassid = row[Long]("refclassid"),
        refobjid = row[Long]("refobjid"),
        deptype = row[String]("deptype")
      )
    )
  }

  implicit val oFormat: OFormat[PgShdependRow] = new OFormat[PgShdependRow]{
    override def writes(o: PgShdependRow): JsObject =
      Json.obj(
        "dbid" -> o.dbid,
      "classid" -> o.classid,
      "objid" -> o.objid,
      "objsubid" -> o.objsubid,
      "refclassid" -> o.refclassid,
      "refobjid" -> o.refobjid,
      "deptype" -> o.deptype
      )

    override def reads(json: JsValue): JsResult[PgShdependRow] = {
      JsResult.fromTry(
        Try(
          PgShdependRow(
            dbid = json.\("dbid").as[Long],
            classid = json.\("classid").as[Long],
            objid = json.\("objid").as[Long],
            objsubid = json.\("objsubid").as[Int],
            refclassid = json.\("refclassid").as[Long],
            refobjid = json.\("refobjid").as[Long],
            deptype = json.\("deptype").as[String]
          )
        )
      )
    }
  }
}
