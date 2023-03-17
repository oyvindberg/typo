package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class DomainUdtUsageRow(
  udtCatalog: /* unknown nullability */ Option[String],
  udtSchema: /* unknown nullability */ Option[String],
  udtName: /* unknown nullability */ Option[String],
  domainCatalog: /* unknown nullability */ Option[String],
  domainSchema: /* unknown nullability */ Option[String],
  domainName: /* unknown nullability */ Option[String]
)

object DomainUdtUsageRow {
  implicit val rowParser: RowParser[DomainUdtUsageRow] = { row =>
    Success(
      DomainUdtUsageRow(
        udtCatalog = row[/* unknown nullability */ Option[String]]("udt_catalog"),
        udtSchema = row[/* unknown nullability */ Option[String]]("udt_schema"),
        udtName = row[/* unknown nullability */ Option[String]]("udt_name"),
        domainCatalog = row[/* unknown nullability */ Option[String]]("domain_catalog"),
        domainSchema = row[/* unknown nullability */ Option[String]]("domain_schema"),
        domainName = row[/* unknown nullability */ Option[String]]("domain_name")
      )
    )
  }

  implicit val oFormat: OFormat[DomainUdtUsageRow] = new OFormat[DomainUdtUsageRow]{
    override def writes(o: DomainUdtUsageRow): JsObject =
      Json.obj(
        "udt_catalog" -> o.udtCatalog,
      "udt_schema" -> o.udtSchema,
      "udt_name" -> o.udtName,
      "domain_catalog" -> o.domainCatalog,
      "domain_schema" -> o.domainSchema,
      "domain_name" -> o.domainName
      )

    override def reads(json: JsValue): JsResult[DomainUdtUsageRow] = {
      JsResult.fromTry(
        Try(
          DomainUdtUsageRow(
            udtCatalog = json.\("udt_catalog").toOption.map(_.as[String]),
            udtSchema = json.\("udt_schema").toOption.map(_.as[String]),
            udtName = json.\("udt_name").toOption.map(_.as[String]),
            domainCatalog = json.\("domain_catalog").toOption.map(_.as[String]),
            domainSchema = json.\("domain_schema").toOption.map(_.as[String]),
            domainName = json.\("domain_name").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
