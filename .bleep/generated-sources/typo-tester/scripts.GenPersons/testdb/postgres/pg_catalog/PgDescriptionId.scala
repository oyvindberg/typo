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

case class PgDescriptionId(objoid: Long, classoid: Long, objsubid: Int)
object PgDescriptionId {
  implicit val ordering: Ordering[PgDescriptionId] = Ordering.by(x => (x.objoid, x.classoid, x.objsubid))
  implicit val oFormat: OFormat[PgDescriptionId] = new OFormat[PgDescriptionId]{
    override def writes(o: PgDescriptionId): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
      "classoid" -> o.classoid,
      "objsubid" -> o.objsubid
      )

    override def reads(json: JsValue): JsResult[PgDescriptionId] = {
      JsResult.fromTry(
        Try(
          PgDescriptionId(
            objoid = json.\("objoid").as[Long],
            classoid = json.\("classoid").as[Long],
            objsubid = json.\("objsubid").as[Int]
          )
        )
      )
    }
  }
  implicit val rowParser: RowParser[PgDescriptionId] = { row =>
    Success(
      PgDescriptionId(
        objoid = row[Long]("objoid"),
        classoid = row[Long]("classoid"),
        objsubid = row[Int]("objsubid")
      )
    )
  }

}
