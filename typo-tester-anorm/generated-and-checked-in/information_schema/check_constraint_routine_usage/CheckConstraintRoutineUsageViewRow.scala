/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package check_constraint_routine_usage

import adventureworks.information_schema.SqlIdentifier
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class CheckConstraintRoutineUsageViewRow(
  constraintCatalog: Option[SqlIdentifier],
  constraintSchema: Option[SqlIdentifier],
  constraintName: Option[SqlIdentifier],
  specificCatalog: Option[SqlIdentifier],
  specificSchema: Option[SqlIdentifier],
  specificName: Option[SqlIdentifier]
)

object CheckConstraintRoutineUsageViewRow {
  def rowParser(idx: Int): RowParser[CheckConstraintRoutineUsageViewRow] =
    RowParser[CheckConstraintRoutineUsageViewRow] { row =>
      Success(
        CheckConstraintRoutineUsageViewRow(
          constraintCatalog = row[Option[SqlIdentifier]](idx + 0),
          constraintSchema = row[Option[SqlIdentifier]](idx + 1),
          constraintName = row[Option[SqlIdentifier]](idx + 2),
          specificCatalog = row[Option[SqlIdentifier]](idx + 3),
          specificSchema = row[Option[SqlIdentifier]](idx + 4),
          specificName = row[Option[SqlIdentifier]](idx + 5)
        )
      )
    }
  implicit val oFormat: OFormat[CheckConstraintRoutineUsageViewRow] = new OFormat[CheckConstraintRoutineUsageViewRow]{
    override def writes(o: CheckConstraintRoutineUsageViewRow): JsObject =
      Json.obj(
        "constraint_catalog" -> o.constraintCatalog,
        "constraint_schema" -> o.constraintSchema,
        "constraint_name" -> o.constraintName,
        "specific_catalog" -> o.specificCatalog,
        "specific_schema" -> o.specificSchema,
        "specific_name" -> o.specificName
      )
  
    override def reads(json: JsValue): JsResult[CheckConstraintRoutineUsageViewRow] = {
      JsResult.fromTry(
        Try(
          CheckConstraintRoutineUsageViewRow(
            constraintCatalog = json.\("constraint_catalog").toOption.map(_.as[SqlIdentifier]),
            constraintSchema = json.\("constraint_schema").toOption.map(_.as[SqlIdentifier]),
            constraintName = json.\("constraint_name").toOption.map(_.as[SqlIdentifier]),
            specificCatalog = json.\("specific_catalog").toOption.map(_.as[SqlIdentifier]),
            specificSchema = json.\("specific_schema").toOption.map(_.as[SqlIdentifier]),
            specificName = json.\("specific_name").toOption.map(_.as[SqlIdentifier])
          )
        )
      )
    }
  }
}