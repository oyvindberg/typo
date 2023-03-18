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

case class PgIndexRow(
  indexrelid: Long,
  indrelid: Long,
  indnatts: Short,
  indnkeyatts: Short,
  indisunique: Boolean,
  indisprimary: Boolean,
  indisexclusion: Boolean,
  indimmediate: Boolean,
  indisclustered: Boolean,
  indisvalid: Boolean,
  indcheckxmin: Boolean,
  indisready: Boolean,
  indislive: Boolean,
  indisreplident: Boolean,
  indkey: String,
  indcollation: String,
  indclass: String,
  indoption: String,
  indexprs: Option[String],
  indpred: Option[String]
)

object PgIndexRow {
  implicit val rowParser: RowParser[PgIndexRow] = { row =>
    Success(
      PgIndexRow(
        indexrelid = row[Long]("indexrelid"),
        indrelid = row[Long]("indrelid"),
        indnatts = row[Short]("indnatts"),
        indnkeyatts = row[Short]("indnkeyatts"),
        indisunique = row[Boolean]("indisunique"),
        indisprimary = row[Boolean]("indisprimary"),
        indisexclusion = row[Boolean]("indisexclusion"),
        indimmediate = row[Boolean]("indimmediate"),
        indisclustered = row[Boolean]("indisclustered"),
        indisvalid = row[Boolean]("indisvalid"),
        indcheckxmin = row[Boolean]("indcheckxmin"),
        indisready = row[Boolean]("indisready"),
        indislive = row[Boolean]("indislive"),
        indisreplident = row[Boolean]("indisreplident"),
        indkey = row[String]("indkey"),
        indcollation = row[String]("indcollation"),
        indclass = row[String]("indclass"),
        indoption = row[String]("indoption"),
        indexprs = row[Option[String]]("indexprs"),
        indpred = row[Option[String]]("indpred")
      )
    )
  }

  implicit val oFormat: OFormat[PgIndexRow] = new OFormat[PgIndexRow]{
    override def writes(o: PgIndexRow): JsObject =
      Json.obj(
        "indexrelid" -> o.indexrelid,
      "indrelid" -> o.indrelid,
      "indnatts" -> o.indnatts,
      "indnkeyatts" -> o.indnkeyatts,
      "indisunique" -> o.indisunique,
      "indisprimary" -> o.indisprimary,
      "indisexclusion" -> o.indisexclusion,
      "indimmediate" -> o.indimmediate,
      "indisclustered" -> o.indisclustered,
      "indisvalid" -> o.indisvalid,
      "indcheckxmin" -> o.indcheckxmin,
      "indisready" -> o.indisready,
      "indislive" -> o.indislive,
      "indisreplident" -> o.indisreplident,
      "indkey" -> o.indkey,
      "indcollation" -> o.indcollation,
      "indclass" -> o.indclass,
      "indoption" -> o.indoption,
      "indexprs" -> o.indexprs,
      "indpred" -> o.indpred
      )

    override def reads(json: JsValue): JsResult[PgIndexRow] = {
      JsResult.fromTry(
        Try(
          PgIndexRow(
            indexrelid = json.\("indexrelid").as[Long],
            indrelid = json.\("indrelid").as[Long],
            indnatts = json.\("indnatts").as[Short],
            indnkeyatts = json.\("indnkeyatts").as[Short],
            indisunique = json.\("indisunique").as[Boolean],
            indisprimary = json.\("indisprimary").as[Boolean],
            indisexclusion = json.\("indisexclusion").as[Boolean],
            indimmediate = json.\("indimmediate").as[Boolean],
            indisclustered = json.\("indisclustered").as[Boolean],
            indisvalid = json.\("indisvalid").as[Boolean],
            indcheckxmin = json.\("indcheckxmin").as[Boolean],
            indisready = json.\("indisready").as[Boolean],
            indislive = json.\("indislive").as[Boolean],
            indisreplident = json.\("indisreplident").as[Boolean],
            indkey = json.\("indkey").as[String],
            indcollation = json.\("indcollation").as[String],
            indclass = json.\("indclass").as[String],
            indoption = json.\("indoption").as[String],
            indexprs = json.\("indexprs").toOption.map(_.as[String]),
            indpred = json.\("indpred").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
