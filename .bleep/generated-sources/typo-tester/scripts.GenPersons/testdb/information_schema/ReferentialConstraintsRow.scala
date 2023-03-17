package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class ReferentialConstraintsRow(
  constraintCatalog: /* unknown nullability */ Option[String],
  constraintSchema: /* unknown nullability */ Option[String],
  constraintName: /* unknown nullability */ Option[String],
  uniqueConstraintCatalog: /* unknown nullability */ Option[String],
  uniqueConstraintSchema: /* unknown nullability */ Option[String],
  uniqueConstraintName: /* unknown nullability */ Option[String],
  matchOption: /* unknown nullability */ Option[String],
  updateRule: /* unknown nullability */ Option[String],
  deleteRule: /* unknown nullability */ Option[String]
)

object ReferentialConstraintsRow {
  implicit val rowParser: RowParser[ReferentialConstraintsRow] = { row =>
    Success(
      ReferentialConstraintsRow(
        constraintCatalog = row[/* unknown nullability */ Option[String]]("constraint_catalog"),
        constraintSchema = row[/* unknown nullability */ Option[String]]("constraint_schema"),
        constraintName = row[/* unknown nullability */ Option[String]]("constraint_name"),
        uniqueConstraintCatalog = row[/* unknown nullability */ Option[String]]("unique_constraint_catalog"),
        uniqueConstraintSchema = row[/* unknown nullability */ Option[String]]("unique_constraint_schema"),
        uniqueConstraintName = row[/* unknown nullability */ Option[String]]("unique_constraint_name"),
        matchOption = row[/* unknown nullability */ Option[String]]("match_option"),
        updateRule = row[/* unknown nullability */ Option[String]]("update_rule"),
        deleteRule = row[/* unknown nullability */ Option[String]]("delete_rule")
      )
    )
  }

  implicit val oFormat: OFormat[ReferentialConstraintsRow] = new OFormat[ReferentialConstraintsRow]{
    override def writes(o: ReferentialConstraintsRow): JsObject =
      Json.obj(
        "constraint_catalog" -> o.constraintCatalog,
      "constraint_schema" -> o.constraintSchema,
      "constraint_name" -> o.constraintName,
      "unique_constraint_catalog" -> o.uniqueConstraintCatalog,
      "unique_constraint_schema" -> o.uniqueConstraintSchema,
      "unique_constraint_name" -> o.uniqueConstraintName,
      "match_option" -> o.matchOption,
      "update_rule" -> o.updateRule,
      "delete_rule" -> o.deleteRule
      )

    override def reads(json: JsValue): JsResult[ReferentialConstraintsRow] = {
      JsResult.fromTry(
        Try(
          ReferentialConstraintsRow(
            constraintCatalog = json.\("constraint_catalog").toOption.map(_.as[String]),
            constraintSchema = json.\("constraint_schema").toOption.map(_.as[String]),
            constraintName = json.\("constraint_name").toOption.map(_.as[String]),
            uniqueConstraintCatalog = json.\("unique_constraint_catalog").toOption.map(_.as[String]),
            uniqueConstraintSchema = json.\("unique_constraint_schema").toOption.map(_.as[String]),
            uniqueConstraintName = json.\("unique_constraint_name").toOption.map(_.as[String]),
            matchOption = json.\("match_option").toOption.map(_.as[String]),
            updateRule = json.\("update_rule").toOption.map(_.as[String]),
            deleteRule = json.\("delete_rule").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
