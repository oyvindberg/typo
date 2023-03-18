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

case class PgShseclabelRow(
  objoid: Long,
  classoid: Long,
  provider: String,
  label: String
){
  val objoidAndClassoidAndProvider: PgShseclabelId = PgShseclabelId(objoid, classoid, provider)
}

object PgShseclabelRow {
  implicit val rowParser: RowParser[PgShseclabelRow] = { row =>
    Success(
      PgShseclabelRow(
        objoid = row[Long]("objoid"),
        classoid = row[Long]("classoid"),
        provider = row[String]("provider"),
        label = row[String]("label")
      )
    )
  }

  implicit val oFormat: OFormat[PgShseclabelRow] = new OFormat[PgShseclabelRow]{
    override def writes(o: PgShseclabelRow): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
      "classoid" -> o.classoid,
      "provider" -> o.provider,
      "label" -> o.label
      )

    override def reads(json: JsValue): JsResult[PgShseclabelRow] = {
      JsResult.fromTry(
        Try(
          PgShseclabelRow(
            objoid = json.\("objoid").as[Long],
            classoid = json.\("classoid").as[Long],
            provider = json.\("provider").as[String],
            label = json.\("label").as[String]
          )
        )
      )
    }
  }
}
