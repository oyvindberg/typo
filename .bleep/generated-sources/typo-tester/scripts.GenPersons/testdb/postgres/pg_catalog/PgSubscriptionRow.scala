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

case class PgSubscriptionRow(
  oid: PgSubscriptionId,
  subdbid: Long,
  subname: String,
  subowner: Long,
  subenabled: Boolean,
  subbinary: Boolean,
  substream: Boolean,
  subconninfo: String,
  subslotname: Option[String],
  subsynccommit: String,
  subpublications: Array[String]
)

object PgSubscriptionRow {
  implicit val rowParser: RowParser[PgSubscriptionRow] = { row =>
    Success(
      PgSubscriptionRow(
        oid = row[PgSubscriptionId]("oid"),
        subdbid = row[Long]("subdbid"),
        subname = row[String]("subname"),
        subowner = row[Long]("subowner"),
        subenabled = row[Boolean]("subenabled"),
        subbinary = row[Boolean]("subbinary"),
        substream = row[Boolean]("substream"),
        subconninfo = row[String]("subconninfo"),
        subslotname = row[Option[String]]("subslotname"),
        subsynccommit = row[String]("subsynccommit"),
        subpublications = row[Array[String]]("subpublications")
      )
    )
  }

  implicit val oFormat: OFormat[PgSubscriptionRow] = new OFormat[PgSubscriptionRow]{
    override def writes(o: PgSubscriptionRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "subdbid" -> o.subdbid,
      "subname" -> o.subname,
      "subowner" -> o.subowner,
      "subenabled" -> o.subenabled,
      "subbinary" -> o.subbinary,
      "substream" -> o.substream,
      "subconninfo" -> o.subconninfo,
      "subslotname" -> o.subslotname,
      "subsynccommit" -> o.subsynccommit,
      "subpublications" -> o.subpublications
      )

    override def reads(json: JsValue): JsResult[PgSubscriptionRow] = {
      JsResult.fromTry(
        Try(
          PgSubscriptionRow(
            oid = json.\("oid").as[PgSubscriptionId],
            subdbid = json.\("subdbid").as[Long],
            subname = json.\("subname").as[String],
            subowner = json.\("subowner").as[Long],
            subenabled = json.\("subenabled").as[Boolean],
            subbinary = json.\("subbinary").as[Boolean],
            substream = json.\("substream").as[Boolean],
            subconninfo = json.\("subconninfo").as[String],
            subslotname = json.\("subslotname").toOption.map(_.as[String]),
            subsynccommit = json.\("subsynccommit").as[String],
            subpublications = json.\("subpublications").as[Array[String]]
          )
        )
      )
    }
  }
}
