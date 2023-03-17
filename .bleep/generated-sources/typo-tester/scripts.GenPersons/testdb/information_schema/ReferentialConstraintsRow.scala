package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[ReferentialConstraintsRow] = Json.format
}
