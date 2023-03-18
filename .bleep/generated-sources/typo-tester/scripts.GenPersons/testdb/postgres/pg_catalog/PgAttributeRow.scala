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

case class PgAttributeRow(
  attrelid: Long,
  attname: String,
  atttypid: Long,
  attstattarget: Int,
  attlen: Short,
  attnum: Short,
  attndims: Int,
  attcacheoff: Int,
  atttypmod: Int,
  attbyval: Boolean,
  attalign: String,
  attstorage: String,
  attcompression: String,
  attnotnull: Boolean,
  atthasdef: Boolean,
  atthasmissing: Boolean,
  attidentity: String,
  attgenerated: String,
  attisdropped: Boolean,
  attislocal: Boolean,
  attinhcount: Int,
  attcollation: Long,
  attacl: Option[Array[String]],
  attoptions: Option[Array[String]],
  attfdwoptions: Option[Array[String]],
  attmissingval: Option[String]
){
  val compositeId: PgAttributeId = PgAttributeId(attrelid, attnum)
}

object PgAttributeRow {
  implicit val rowParser: RowParser[PgAttributeRow] = { row =>
    Success(
      PgAttributeRow(
        attrelid = row[Long]("attrelid"),
        attname = row[String]("attname"),
        atttypid = row[Long]("atttypid"),
        attstattarget = row[Int]("attstattarget"),
        attlen = row[Short]("attlen"),
        attnum = row[Short]("attnum"),
        attndims = row[Int]("attndims"),
        attcacheoff = row[Int]("attcacheoff"),
        atttypmod = row[Int]("atttypmod"),
        attbyval = row[Boolean]("attbyval"),
        attalign = row[String]("attalign"),
        attstorage = row[String]("attstorage"),
        attcompression = row[String]("attcompression"),
        attnotnull = row[Boolean]("attnotnull"),
        atthasdef = row[Boolean]("atthasdef"),
        atthasmissing = row[Boolean]("atthasmissing"),
        attidentity = row[String]("attidentity"),
        attgenerated = row[String]("attgenerated"),
        attisdropped = row[Boolean]("attisdropped"),
        attislocal = row[Boolean]("attislocal"),
        attinhcount = row[Int]("attinhcount"),
        attcollation = row[Long]("attcollation"),
        attacl = row[Option[Array[String]]]("attacl"),
        attoptions = row[Option[Array[String]]]("attoptions"),
        attfdwoptions = row[Option[Array[String]]]("attfdwoptions"),
        attmissingval = row[Option[String]]("attmissingval")
      )
    )
  }

  implicit val oFormat: OFormat[PgAttributeRow] = new OFormat[PgAttributeRow]{
    override def writes(o: PgAttributeRow): JsObject =
      Json.obj(
        "attrelid" -> o.attrelid,
      "attname" -> o.attname,
      "atttypid" -> o.atttypid,
      "attstattarget" -> o.attstattarget,
      "attlen" -> o.attlen,
      "attnum" -> o.attnum,
      "attndims" -> o.attndims,
      "attcacheoff" -> o.attcacheoff,
      "atttypmod" -> o.atttypmod,
      "attbyval" -> o.attbyval,
      "attalign" -> o.attalign,
      "attstorage" -> o.attstorage,
      "attcompression" -> o.attcompression,
      "attnotnull" -> o.attnotnull,
      "atthasdef" -> o.atthasdef,
      "atthasmissing" -> o.atthasmissing,
      "attidentity" -> o.attidentity,
      "attgenerated" -> o.attgenerated,
      "attisdropped" -> o.attisdropped,
      "attislocal" -> o.attislocal,
      "attinhcount" -> o.attinhcount,
      "attcollation" -> o.attcollation,
      "attacl" -> o.attacl,
      "attoptions" -> o.attoptions,
      "attfdwoptions" -> o.attfdwoptions,
      "attmissingval" -> o.attmissingval
      )

    override def reads(json: JsValue): JsResult[PgAttributeRow] = {
      JsResult.fromTry(
        Try(
          PgAttributeRow(
            attrelid = json.\("attrelid").as[Long],
            attname = json.\("attname").as[String],
            atttypid = json.\("atttypid").as[Long],
            attstattarget = json.\("attstattarget").as[Int],
            attlen = json.\("attlen").as[Short],
            attnum = json.\("attnum").as[Short],
            attndims = json.\("attndims").as[Int],
            attcacheoff = json.\("attcacheoff").as[Int],
            atttypmod = json.\("atttypmod").as[Int],
            attbyval = json.\("attbyval").as[Boolean],
            attalign = json.\("attalign").as[String],
            attstorage = json.\("attstorage").as[String],
            attcompression = json.\("attcompression").as[String],
            attnotnull = json.\("attnotnull").as[Boolean],
            atthasdef = json.\("atthasdef").as[Boolean],
            atthasmissing = json.\("atthasmissing").as[Boolean],
            attidentity = json.\("attidentity").as[String],
            attgenerated = json.\("attgenerated").as[String],
            attisdropped = json.\("attisdropped").as[Boolean],
            attislocal = json.\("attislocal").as[Boolean],
            attinhcount = json.\("attinhcount").as[Int],
            attcollation = json.\("attcollation").as[Long],
            attacl = json.\("attacl").toOption.map(_.as[Array[String]]),
            attoptions = json.\("attoptions").toOption.map(_.as[Array[String]]),
            attfdwoptions = json.\("attfdwoptions").toOption.map(_.as[Array[String]]),
            attmissingval = json.\("attmissingval").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
