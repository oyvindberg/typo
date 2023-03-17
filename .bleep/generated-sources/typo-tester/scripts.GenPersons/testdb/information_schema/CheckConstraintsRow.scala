package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class CheckConstraintsRow(
  constraintCatalog: /* unknown nullability */ Option[String],
  constraintSchema: /* unknown nullability */ Option[String],
  constraintName: /* unknown nullability */ Option[String],
  checkClause: /* unknown nullability */ Option[String]
)

object CheckConstraintsRow {
  implicit val rowParser: RowParser[CheckConstraintsRow] = { row =>
    Success(
      CheckConstraintsRow(
        constraintCatalog = row[/* unknown nullability */ Option[String]]("constraint_catalog"),
        constraintSchema = row[/* unknown nullability */ Option[String]]("constraint_schema"),
        constraintName = row[/* unknown nullability */ Option[String]]("constraint_name"),
        checkClause = row[/* unknown nullability */ Option[String]]("check_clause")
      )
    )
  }

  implicit val oFormat: OFormat[CheckConstraintsRow] = Json.format
}
