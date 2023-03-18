package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgPreparedStatementsRow(
  name: /* unknown nullability */ Option[String],
  statement: /* unknown nullability */ Option[String],
  prepareTime: /* unknown nullability */ Option[LocalDateTime],
  parameterTypes: /* unknown nullability */ Option[Array[String]],
  fromSql: /* unknown nullability */ Option[Boolean],
  genericPlans: /* unknown nullability */ Option[Long],
  customPlans: /* unknown nullability */ Option[Long]
)

object PgPreparedStatementsRow {
  implicit val rowParser: RowParser[PgPreparedStatementsRow] = { row =>
    Success(
      PgPreparedStatementsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        statement = row[/* unknown nullability */ Option[String]]("statement"),
        prepareTime = row[/* unknown nullability */ Option[LocalDateTime]]("prepare_time"),
        parameterTypes = row[/* unknown nullability */ Option[Array[String]]]("parameter_types"),
        fromSql = row[/* unknown nullability */ Option[Boolean]]("from_sql"),
        genericPlans = row[/* unknown nullability */ Option[Long]]("generic_plans"),
        customPlans = row[/* unknown nullability */ Option[Long]]("custom_plans")
      )
    )
  }

  implicit val oFormat: OFormat[PgPreparedStatementsRow] = new OFormat[PgPreparedStatementsRow]{
    override def writes(o: PgPreparedStatementsRow): JsObject =
      Json.obj(
        "name" -> o.name,
      "statement" -> o.statement,
      "prepare_time" -> o.prepareTime,
      "parameter_types" -> o.parameterTypes,
      "from_sql" -> o.fromSql,
      "generic_plans" -> o.genericPlans,
      "custom_plans" -> o.customPlans
      )

    override def reads(json: JsValue): JsResult[PgPreparedStatementsRow] = {
      JsResult.fromTry(
        Try(
          PgPreparedStatementsRow(
            name = json.\("name").toOption.map(_.as[String]),
            statement = json.\("statement").toOption.map(_.as[String]),
            prepareTime = json.\("prepare_time").toOption.map(_.as[LocalDateTime]),
            parameterTypes = json.\("parameter_types").toOption.map(_.as[Array[String]]),
            fromSql = json.\("from_sql").toOption.map(_.as[Boolean]),
            genericPlans = json.\("generic_plans").toOption.map(_.as[Long]),
            customPlans = json.\("custom_plans").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
