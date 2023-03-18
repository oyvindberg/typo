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

case class PgStatUserFunctionsRow(
  /** Points to [[PgProcRow.oid]] */
  funcid: Long,
  /** Points to [[PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[PgProcRow.proname]] */
  funcname: String,
  calls: /* unknown nullability */ Option[Long],
  totalTime: /* unknown nullability */ Option[Double],
  selfTime: /* unknown nullability */ Option[Double]
)

object PgStatUserFunctionsRow {
  implicit val rowParser: RowParser[PgStatUserFunctionsRow] = { row =>
    Success(
      PgStatUserFunctionsRow(
        funcid = row[Long]("funcid"),
        schemaname = row[String]("schemaname"),
        funcname = row[String]("funcname"),
        calls = row[/* unknown nullability */ Option[Long]]("calls"),
        totalTime = row[/* unknown nullability */ Option[Double]]("total_time"),
        selfTime = row[/* unknown nullability */ Option[Double]]("self_time")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatUserFunctionsRow] = new OFormat[PgStatUserFunctionsRow]{
    override def writes(o: PgStatUserFunctionsRow): JsObject =
      Json.obj(
        "funcid" -> o.funcid,
      "schemaname" -> o.schemaname,
      "funcname" -> o.funcname,
      "calls" -> o.calls,
      "total_time" -> o.totalTime,
      "self_time" -> o.selfTime
      )

    override def reads(json: JsValue): JsResult[PgStatUserFunctionsRow] = {
      JsResult.fromTry(
        Try(
          PgStatUserFunctionsRow(
            funcid = json.\("funcid").as[Long],
            schemaname = json.\("schemaname").as[String],
            funcname = json.\("funcname").as[String],
            calls = json.\("calls").toOption.map(_.as[Long]),
            totalTime = json.\("total_time").toOption.map(_.as[Double]),
            selfTime = json.\("self_time").toOption.map(_.as[Double])
          )
        )
      )
    }
  }
}
