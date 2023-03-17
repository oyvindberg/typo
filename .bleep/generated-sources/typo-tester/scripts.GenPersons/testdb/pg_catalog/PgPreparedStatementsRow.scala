package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgPreparedStatementsRow(
  name: /* unknown nullability */ Option[String],
  statement: /* unknown nullability */ Option[String],
  prepareTime: /* unknown nullability */ Option[LocalDateTime],
  parameterTypes: /* typo doesn't know how to translate: columnType: Array, columnTypeName: _regtype, columnClassName: java.sql.Array */ Any,
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
        parameterTypes = row[/* typo doesn't know how to translate: columnType: Array, columnTypeName: _regtype, columnClassName: java.sql.Array */ Any]("parameter_types"),
        fromSql = row[/* unknown nullability */ Option[Boolean]]("from_sql"),
        genericPlans = row[/* unknown nullability */ Option[Long]]("generic_plans"),
        customPlans = row[/* unknown nullability */ Option[Long]]("custom_plans")
      )
    )
  }

  implicit val oFormat: OFormat[PgPreparedStatementsRow] = Json.format
}
