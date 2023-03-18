package testdb
package postgres
package information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class CheckConstraintRoutineUsageRow(
  constraintCatalog: /* unknown nullability */ Option[String],
  constraintSchema: /* unknown nullability */ Option[String],
  constraintName: /* unknown nullability */ Option[String],
  specificCatalog: /* unknown nullability */ Option[String],
  specificSchema: /* unknown nullability */ Option[String],
  specificName: /* unknown nullability */ Option[String]
)

object CheckConstraintRoutineUsageRow {
  implicit val rowParser: RowParser[CheckConstraintRoutineUsageRow] = { row =>
    Success(
      CheckConstraintRoutineUsageRow(
        constraintCatalog = row[/* unknown nullability */ Option[String]]("constraint_catalog"),
        constraintSchema = row[/* unknown nullability */ Option[String]]("constraint_schema"),
        constraintName = row[/* unknown nullability */ Option[String]]("constraint_name"),
        specificCatalog = row[/* unknown nullability */ Option[String]]("specific_catalog"),
        specificSchema = row[/* unknown nullability */ Option[String]]("specific_schema"),
        specificName = row[/* unknown nullability */ Option[String]]("specific_name")
      )
    )
  }

  implicit val oFormat: OFormat[CheckConstraintRoutineUsageRow] = new OFormat[CheckConstraintRoutineUsageRow]{
    override def writes(o: CheckConstraintRoutineUsageRow): JsObject =
      Json.obj(
        "constraint_catalog" -> o.constraintCatalog,
      "constraint_schema" -> o.constraintSchema,
      "constraint_name" -> o.constraintName,
      "specific_catalog" -> o.specificCatalog,
      "specific_schema" -> o.specificSchema,
      "specific_name" -> o.specificName
      )

    override def reads(json: JsValue): JsResult[CheckConstraintRoutineUsageRow] = {
      JsResult.fromTry(
        Try(
          CheckConstraintRoutineUsageRow(
            constraintCatalog = json.\("constraint_catalog").toOption.map(_.as[String]),
            constraintSchema = json.\("constraint_schema").toOption.map(_.as[String]),
            constraintName = json.\("constraint_name").toOption.map(_.as[String]),
            specificCatalog = json.\("specific_catalog").toOption.map(_.as[String]),
            specificSchema = json.\("specific_schema").toOption.map(_.as[String]),
            specificName = json.\("specific_name").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
