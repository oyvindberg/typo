/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_shdescription

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

/** Type for the composite primary key of table `pg_catalog.pg_shdescription` */
case class PgShdescriptionId(objoid: /* oid */ Long, classoid: /* oid */ Long)
object PgShdescriptionId {
  implicit lazy val ordering: Ordering[PgShdescriptionId] = Ordering.by(x => (x.objoid, x.classoid))
  implicit lazy val reads: Reads[PgShdescriptionId] = Reads[PgShdescriptionId](json => JsResult.fromTry(
      Try(
        PgShdescriptionId(
          objoid = json.\("objoid").as(Reads.LongReads),
          classoid = json.\("classoid").as(Reads.LongReads)
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[PgShdescriptionId] = OWrites[PgShdescriptionId](o =>
    new JsObject(ListMap[String, JsValue](
      "objoid" -> Writes.LongWrites.writes(o.objoid),
      "classoid" -> Writes.LongWrites.writes(o.classoid)
    ))
  )
}