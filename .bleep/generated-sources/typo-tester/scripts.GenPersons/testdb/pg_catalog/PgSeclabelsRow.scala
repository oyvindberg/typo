package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgSeclabelsRow(
  objoid: /* unknown nullability */ Option[Long],
  classoid: /* unknown nullability */ Option[Long],
  objsubid: /* unknown nullability */ Option[Int],
  objtype: /* unknown nullability */ Option[String],
  objnamespace: /* unknown nullability */ Option[Long],
  objname: /* unknown nullability */ Option[String],
  provider: /* unknown nullability */ Option[String],
  label: /* unknown nullability */ Option[String]
)

object PgSeclabelsRow {
  implicit val rowParser: RowParser[PgSeclabelsRow] = { row =>
    Success(
      PgSeclabelsRow(
        objoid = row[/* unknown nullability */ Option[Long]]("objoid"),
        classoid = row[/* unknown nullability */ Option[Long]]("classoid"),
        objsubid = row[/* unknown nullability */ Option[Int]]("objsubid"),
        objtype = row[/* unknown nullability */ Option[String]]("objtype"),
        objnamespace = row[/* unknown nullability */ Option[Long]]("objnamespace"),
        objname = row[/* unknown nullability */ Option[String]]("objname"),
        provider = row[/* unknown nullability */ Option[String]]("provider"),
        label = row[/* unknown nullability */ Option[String]]("label")
      )
    )
  }

  implicit val oFormat: OFormat[PgSeclabelsRow] = new OFormat[PgSeclabelsRow]{
    override def writes(o: PgSeclabelsRow): JsObject =
      Json.obj(
        "objoid" -> o.objoid,
      "classoid" -> o.classoid,
      "objsubid" -> o.objsubid,
      "objtype" -> o.objtype,
      "objnamespace" -> o.objnamespace,
      "objname" -> o.objname,
      "provider" -> o.provider,
      "label" -> o.label
      )

    override def reads(json: JsValue): JsResult[PgSeclabelsRow] = {
      JsResult.fromTry(
        Try(
          PgSeclabelsRow(
            objoid = json.\("objoid").toOption.map(_.as[Long]),
            classoid = json.\("classoid").toOption.map(_.as[Long]),
            objsubid = json.\("objsubid").toOption.map(_.as[Int]),
            objtype = json.\("objtype").toOption.map(_.as[String]),
            objnamespace = json.\("objnamespace").toOption.map(_.as[Long]),
            objname = json.\("objname").toOption.map(_.as[String]),
            provider = json.\("provider").toOption.map(_.as[String]),
            label = json.\("label").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
