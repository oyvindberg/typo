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

case class PgSettingsRow(
  name: /* unknown nullability */ Option[String],
  setting: /* unknown nullability */ Option[String],
  unit: /* unknown nullability */ Option[String],
  category: /* unknown nullability */ Option[String],
  shortDesc: /* unknown nullability */ Option[String],
  extraDesc: /* unknown nullability */ Option[String],
  context: /* unknown nullability */ Option[String],
  vartype: /* unknown nullability */ Option[String],
  source: /* unknown nullability */ Option[String],
  minVal: /* unknown nullability */ Option[String],
  maxVal: /* unknown nullability */ Option[String],
  enumvals: /* unknown nullability */ Option[Array[String]],
  bootVal: /* unknown nullability */ Option[String],
  resetVal: /* unknown nullability */ Option[String],
  sourcefile: /* unknown nullability */ Option[String],
  sourceline: /* unknown nullability */ Option[Int],
  pendingRestart: /* unknown nullability */ Option[Boolean]
)

object PgSettingsRow {
  implicit val rowParser: RowParser[PgSettingsRow] = { row =>
    Success(
      PgSettingsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        setting = row[/* unknown nullability */ Option[String]]("setting"),
        unit = row[/* unknown nullability */ Option[String]]("unit"),
        category = row[/* unknown nullability */ Option[String]]("category"),
        shortDesc = row[/* unknown nullability */ Option[String]]("short_desc"),
        extraDesc = row[/* unknown nullability */ Option[String]]("extra_desc"),
        context = row[/* unknown nullability */ Option[String]]("context"),
        vartype = row[/* unknown nullability */ Option[String]]("vartype"),
        source = row[/* unknown nullability */ Option[String]]("source"),
        minVal = row[/* unknown nullability */ Option[String]]("min_val"),
        maxVal = row[/* unknown nullability */ Option[String]]("max_val"),
        enumvals = row[/* unknown nullability */ Option[Array[String]]]("enumvals"),
        bootVal = row[/* unknown nullability */ Option[String]]("boot_val"),
        resetVal = row[/* unknown nullability */ Option[String]]("reset_val"),
        sourcefile = row[/* unknown nullability */ Option[String]]("sourcefile"),
        sourceline = row[/* unknown nullability */ Option[Int]]("sourceline"),
        pendingRestart = row[/* unknown nullability */ Option[Boolean]]("pending_restart")
      )
    )
  }

  implicit val oFormat: OFormat[PgSettingsRow] = new OFormat[PgSettingsRow]{
    override def writes(o: PgSettingsRow): JsObject =
      Json.obj(
        "name" -> o.name,
      "setting" -> o.setting,
      "unit" -> o.unit,
      "category" -> o.category,
      "short_desc" -> o.shortDesc,
      "extra_desc" -> o.extraDesc,
      "context" -> o.context,
      "vartype" -> o.vartype,
      "source" -> o.source,
      "min_val" -> o.minVal,
      "max_val" -> o.maxVal,
      "enumvals" -> o.enumvals,
      "boot_val" -> o.bootVal,
      "reset_val" -> o.resetVal,
      "sourcefile" -> o.sourcefile,
      "sourceline" -> o.sourceline,
      "pending_restart" -> o.pendingRestart
      )

    override def reads(json: JsValue): JsResult[PgSettingsRow] = {
      JsResult.fromTry(
        Try(
          PgSettingsRow(
            name = json.\("name").toOption.map(_.as[String]),
            setting = json.\("setting").toOption.map(_.as[String]),
            unit = json.\("unit").toOption.map(_.as[String]),
            category = json.\("category").toOption.map(_.as[String]),
            shortDesc = json.\("short_desc").toOption.map(_.as[String]),
            extraDesc = json.\("extra_desc").toOption.map(_.as[String]),
            context = json.\("context").toOption.map(_.as[String]),
            vartype = json.\("vartype").toOption.map(_.as[String]),
            source = json.\("source").toOption.map(_.as[String]),
            minVal = json.\("min_val").toOption.map(_.as[String]),
            maxVal = json.\("max_val").toOption.map(_.as[String]),
            enumvals = json.\("enumvals").toOption.map(_.as[Array[String]]),
            bootVal = json.\("boot_val").toOption.map(_.as[String]),
            resetVal = json.\("reset_val").toOption.map(_.as[String]),
            sourcefile = json.\("sourcefile").toOption.map(_.as[String]),
            sourceline = json.\("sourceline").toOption.map(_.as[Int]),
            pendingRestart = json.\("pending_restart").toOption.map(_.as[Boolean])
          )
        )
      )
    }
  }
}
