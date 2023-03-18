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

case class PgDescriptionRow(
  objoid: Long,
  classoid: Long,
  objsubid: Int,
  description: String
)

object PgDescriptionRow {
  implicit val rowParser: RowParser[PgDescriptionRow] = { row =>
    Success(
      PgDescriptionRow(
        objoid = row[Long]("objoid"),
        classoid = row[Long]("classoid"),
        objsubid = row[Int]("objsubid"),
        description = row[String]("description")
      )
    )
  }

  implicit val oFormat: OFormat[PgDescriptionRow] = new OFormat[PgDescriptionRow]{
    override def writes(o: PgDescriptionRow): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
      "classoid" -> o.classoid,
      "objsubid" -> o.objsubid,
      "description" -> o.description
      )

    override def reads(json: JsValue): JsResult[PgDescriptionRow] = {
      JsResult.fromTry(
        Try(
          PgDescriptionRow(
            objoid = json.\("objoid").as[Long],
            classoid = json.\("classoid").as[Long],
            objsubid = json.\("objsubid").as[Int],
            description = json.\("description").as[String]
          )
        )
      )
    }
  }
}
