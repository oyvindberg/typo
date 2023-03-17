package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class DomainConstraintsRow(
  constraintCatalog: /* unknown nullability */ Option[String],
  constraintSchema: /* unknown nullability */ Option[String],
  constraintName: /* unknown nullability */ Option[String],
  domainCatalog: /* unknown nullability */ Option[String],
  domainSchema: /* unknown nullability */ Option[String],
  domainName: /* unknown nullability */ Option[String],
  isDeferrable: /* unknown nullability */ Option[String],
  initiallyDeferred: /* unknown nullability */ Option[String]
)

object DomainConstraintsRow {
  implicit val rowParser: RowParser[DomainConstraintsRow] = { row =>
    Success(
      DomainConstraintsRow(
        constraintCatalog = row[/* unknown nullability */ Option[String]]("constraint_catalog"),
        constraintSchema = row[/* unknown nullability */ Option[String]]("constraint_schema"),
        constraintName = row[/* unknown nullability */ Option[String]]("constraint_name"),
        domainCatalog = row[/* unknown nullability */ Option[String]]("domain_catalog"),
        domainSchema = row[/* unknown nullability */ Option[String]]("domain_schema"),
        domainName = row[/* unknown nullability */ Option[String]]("domain_name"),
        isDeferrable = row[/* unknown nullability */ Option[String]]("is_deferrable"),
        initiallyDeferred = row[/* unknown nullability */ Option[String]]("initially_deferred")
      )
    )
  }

  implicit val oFormat: OFormat[DomainConstraintsRow] = Json.format
}
