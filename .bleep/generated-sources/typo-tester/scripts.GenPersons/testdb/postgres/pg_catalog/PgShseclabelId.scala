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

case class PgShseclabelId(objoid: Long, classoid: Long, provider: String)
object PgShseclabelId {
  implicit val ordering: Ordering[PgShseclabelId] = Ordering.by(x => (x.objoid, x.classoid, x.provider))
  implicit val oFormat: OFormat[PgShseclabelId] = new OFormat[PgShseclabelId]{
    override def writes(o: PgShseclabelId): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
      "classoid" -> o.classoid,
      "provider" -> o.provider
      )

    override def reads(json: JsValue): JsResult[PgShseclabelId] = {
      JsResult.fromTry(
        Try(
          PgShseclabelId(
            objoid = json.\("objoid").as[Long],
            classoid = json.\("classoid").as[Long],
            provider = json.\("provider").as[String]
          )
        )
      )
    }
  }
  implicit val rowParser: RowParser[PgShseclabelId] = { row =>
    Success(
      PgShseclabelId(
        objoid = row[Long]("objoid"),
        classoid = row[Long]("classoid"),
        provider = row[String]("provider")
      )
    )
  }

}
