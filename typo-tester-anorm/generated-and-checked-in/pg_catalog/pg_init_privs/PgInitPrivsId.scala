/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_init_privs

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

/** Type for the composite primary key of table `pg_catalog.pg_init_privs` */
case class PgInitPrivsId(objoid: /* oid */ Long, classoid: /* oid */ Long, objsubid: Int)
object PgInitPrivsId {
  implicit val ordering: Ordering[PgInitPrivsId] = Ordering.by(x => (x.objoid, x.classoid, x.objsubid))
  implicit val oFormat: OFormat[PgInitPrivsId] = new OFormat[PgInitPrivsId]{
    override def writes(o: PgInitPrivsId): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
        "classoid" -> o.classoid,
        "objsubid" -> o.objsubid
      )
  
    override def reads(json: JsValue): JsResult[PgInitPrivsId] = {
      JsResult.fromTry(
        Try(
          PgInitPrivsId(
            objoid = json.\("objoid").as[/* oid */ Long],
            classoid = json.\("classoid").as[/* oid */ Long],
            objsubid = json.\("objsubid").as[Int]
          )
        )
      )
    }
  }
}