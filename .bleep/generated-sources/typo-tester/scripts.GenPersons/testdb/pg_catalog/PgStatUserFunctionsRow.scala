package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatUserFunctionsRow(
  funcid: Long,
  schemaname: String,
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

  implicit val oFormat: OFormat[PgStatUserFunctionsRow] = Json.format
}
