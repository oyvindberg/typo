package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgProcRowUnsaved(
  proname: String,
  pronamespace: Long,
  proowner: Long,
  prolang: Long,
  procost: Float,
  prorows: Float,
  provariadic: Long,
  prosupport: String,
  prokind: String,
  prosecdef: Boolean,
  proleakproof: Boolean,
  proisstrict: Boolean,
  proretset: Boolean,
  provolatile: String,
  proparallel: String,
  pronargs: Short,
  pronargdefaults: Short,
  prorettype: Long,
  proargtypes: String,
  proallargtypes: Option[Array[Long]],
  proargmodes: Option[Array[String]],
  proargnames: Option[Array[String]],
  proargdefaults: Option[String],
  protrftypes: Option[Array[Long]],
  prosrc: String,
  probin: Option[String],
  prosqlbody: Option[String],
  proconfig: Option[Array[String]],
  proacl: Option[Array[String]]
)
object PgProcRowUnsaved {
  implicit val oFormat: OFormat[PgProcRowUnsaved] = new OFormat[PgProcRowUnsaved]{
    override def writes(o: PgProcRowUnsaved): JsObject =
      Json.obj(
        "proname" -> o.proname,
      "pronamespace" -> o.pronamespace,
      "proowner" -> o.proowner,
      "prolang" -> o.prolang,
      "procost" -> o.procost,
      "prorows" -> o.prorows,
      "provariadic" -> o.provariadic,
      "prosupport" -> o.prosupport,
      "prokind" -> o.prokind,
      "prosecdef" -> o.prosecdef,
      "proleakproof" -> o.proleakproof,
      "proisstrict" -> o.proisstrict,
      "proretset" -> o.proretset,
      "provolatile" -> o.provolatile,
      "proparallel" -> o.proparallel,
      "pronargs" -> o.pronargs,
      "pronargdefaults" -> o.pronargdefaults,
      "prorettype" -> o.prorettype,
      "proargtypes" -> o.proargtypes,
      "proallargtypes" -> o.proallargtypes,
      "proargmodes" -> o.proargmodes,
      "proargnames" -> o.proargnames,
      "proargdefaults" -> o.proargdefaults,
      "protrftypes" -> o.protrftypes,
      "prosrc" -> o.prosrc,
      "probin" -> o.probin,
      "prosqlbody" -> o.prosqlbody,
      "proconfig" -> o.proconfig,
      "proacl" -> o.proacl
      )

    override def reads(json: JsValue): JsResult[PgProcRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgProcRowUnsaved(
            proname = json.\("proname").as[String],
            pronamespace = json.\("pronamespace").as[Long],
            proowner = json.\("proowner").as[Long],
            prolang = json.\("prolang").as[Long],
            procost = json.\("procost").as[Float],
            prorows = json.\("prorows").as[Float],
            provariadic = json.\("provariadic").as[Long],
            prosupport = json.\("prosupport").as[String],
            prokind = json.\("prokind").as[String],
            prosecdef = json.\("prosecdef").as[Boolean],
            proleakproof = json.\("proleakproof").as[Boolean],
            proisstrict = json.\("proisstrict").as[Boolean],
            proretset = json.\("proretset").as[Boolean],
            provolatile = json.\("provolatile").as[String],
            proparallel = json.\("proparallel").as[String],
            pronargs = json.\("pronargs").as[Short],
            pronargdefaults = json.\("pronargdefaults").as[Short],
            prorettype = json.\("prorettype").as[Long],
            proargtypes = json.\("proargtypes").as[String],
            proallargtypes = json.\("proallargtypes").toOption.map(_.as[Array[Long]]),
            proargmodes = json.\("proargmodes").toOption.map(_.as[Array[String]]),
            proargnames = json.\("proargnames").toOption.map(_.as[Array[String]]),
            proargdefaults = json.\("proargdefaults").toOption.map(_.as[String]),
            protrftypes = json.\("protrftypes").toOption.map(_.as[Array[Long]]),
            prosrc = json.\("prosrc").as[String],
            probin = json.\("probin").toOption.map(_.as[String]),
            prosqlbody = json.\("prosqlbody").toOption.map(_.as[String]),
            proconfig = json.\("proconfig").toOption.map(_.as[Array[String]]),
            proacl = json.\("proacl").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
