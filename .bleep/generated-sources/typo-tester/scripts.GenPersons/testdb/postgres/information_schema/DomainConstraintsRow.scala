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

  implicit val oFormat: OFormat[DomainConstraintsRow] = new OFormat[DomainConstraintsRow]{
    override def writes(o: DomainConstraintsRow): JsObject =
      Json.obj(
        "constraint_catalog" -> o.constraintCatalog,
      "constraint_schema" -> o.constraintSchema,
      "constraint_name" -> o.constraintName,
      "domain_catalog" -> o.domainCatalog,
      "domain_schema" -> o.domainSchema,
      "domain_name" -> o.domainName,
      "is_deferrable" -> o.isDeferrable,
      "initially_deferred" -> o.initiallyDeferred
      )

    override def reads(json: JsValue): JsResult[DomainConstraintsRow] = {
      JsResult.fromTry(
        Try(
          DomainConstraintsRow(
            constraintCatalog = json.\("constraint_catalog").toOption.map(_.as[String]),
            constraintSchema = json.\("constraint_schema").toOption.map(_.as[String]),
            constraintName = json.\("constraint_name").toOption.map(_.as[String]),
            domainCatalog = json.\("domain_catalog").toOption.map(_.as[String]),
            domainSchema = json.\("domain_schema").toOption.map(_.as[String]),
            domainName = json.\("domain_name").toOption.map(_.as[String]),
            isDeferrable = json.\("is_deferrable").toOption.map(_.as[String]),
            initiallyDeferred = json.\("initially_deferred").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
