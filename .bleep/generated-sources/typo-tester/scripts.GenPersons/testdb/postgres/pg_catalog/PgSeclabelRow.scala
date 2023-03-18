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

case class PgSeclabelRow(
  objoid: Long,
  classoid: Long,
  objsubid: Int,
  provider: String,
  label: String
){
  val compositeId: PgSeclabelId = PgSeclabelId(objoid, classoid, objsubid, provider)
}

object PgSeclabelRow {
  implicit val rowParser: RowParser[PgSeclabelRow] = { row =>
    Success(
      PgSeclabelRow(
        objoid = row[Long]("objoid"),
        classoid = row[Long]("classoid"),
        objsubid = row[Int]("objsubid"),
        provider = row[String]("provider"),
        label = row[String]("label")
      )
    )
  }

  implicit val oFormat: OFormat[PgSeclabelRow] = new OFormat[PgSeclabelRow]{
    override def writes(o: PgSeclabelRow): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
      "classoid" -> o.classoid,
      "objsubid" -> o.objsubid,
      "provider" -> o.provider,
      "label" -> o.label
      )

    override def reads(json: JsValue): JsResult[PgSeclabelRow] = {
      JsResult.fromTry(
        Try(
          PgSeclabelRow(
            objoid = json.\("objoid").as[Long],
            classoid = json.\("classoid").as[Long],
            objsubid = json.\("objsubid").as[Int],
            provider = json.\("provider").as[String],
            label = json.\("label").as[String]
          )
        )
      )
    }
  }
}
