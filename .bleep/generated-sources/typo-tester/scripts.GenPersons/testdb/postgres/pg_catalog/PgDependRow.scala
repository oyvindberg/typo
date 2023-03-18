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

case class PgDependRow(
  classid: Long,
  objid: Long,
  objsubid: Int,
  refclassid: Long,
  refobjid: Long,
  refobjsubid: Int,
  deptype: String
)

object PgDependRow {
  implicit val rowParser: RowParser[PgDependRow] = { row =>
    Success(
      PgDependRow(
        classid = row[Long]("classid"),
        objid = row[Long]("objid"),
        objsubid = row[Int]("objsubid"),
        refclassid = row[Long]("refclassid"),
        refobjid = row[Long]("refobjid"),
        refobjsubid = row[Int]("refobjsubid"),
        deptype = row[String]("deptype")
      )
    )
  }

  implicit val oFormat: OFormat[PgDependRow] = new OFormat[PgDependRow]{
    override def writes(o: PgDependRow): JsObject =
      Json.obj(
        "classid" -> o.classid,
      "objid" -> o.objid,
      "objsubid" -> o.objsubid,
      "refclassid" -> o.refclassid,
      "refobjid" -> o.refobjid,
      "refobjsubid" -> o.refobjsubid,
      "deptype" -> o.deptype
      )

    override def reads(json: JsValue): JsResult[PgDependRow] = {
      JsResult.fromTry(
        Try(
          PgDependRow(
            classid = json.\("classid").as[Long],
            objid = json.\("objid").as[Long],
            objsubid = json.\("objsubid").as[Int],
            refclassid = json.\("refclassid").as[Long],
            refobjid = json.\("refobjid").as[Long],
            refobjsubid = json.\("refobjsubid").as[Int],
            deptype = json.\("deptype").as[String]
          )
        )
      )
    }
  }
}
