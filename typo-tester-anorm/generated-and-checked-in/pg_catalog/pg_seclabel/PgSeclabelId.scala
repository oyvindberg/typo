/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_seclabel

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

/** Type for the composite primary key of table `pg_catalog.pg_seclabel` */
case class PgSeclabelId(objoid: /* oid */ Long, classoid: /* oid */ Long, objsubid: Int, provider: String)
object PgSeclabelId {
  implicit val ordering: Ordering[PgSeclabelId] = Ordering.by(x => (x.objoid, x.classoid, x.objsubid, x.provider))
  implicit val oFormat: OFormat[PgSeclabelId] = new OFormat[PgSeclabelId]{
    override def writes(o: PgSeclabelId): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
        "classoid" -> o.classoid,
        "objsubid" -> o.objsubid,
        "provider" -> o.provider
      )
  
    override def reads(json: JsValue): JsResult[PgSeclabelId] = {
      JsResult.fromTry(
        Try(
          PgSeclabelId(
            objoid = json.\("objoid").as[/* oid */ Long],
            classoid = json.\("classoid").as[/* oid */ Long],
            objsubid = json.\("objsubid").as[Int],
            provider = json.\("provider").as[String]
          )
        )
      )
    }
  }
}