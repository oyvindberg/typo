/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package domains

import adventureworks.information_schema.CardinalNumber
import adventureworks.information_schema.CharacterData
import adventureworks.information_schema.SqlIdentifier
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class DomainsViewRow(
  domainCatalog: Option[SqlIdentifier],
  domainSchema: Option[SqlIdentifier],
  domainName: Option[SqlIdentifier],
  dataType: Option[CharacterData],
  characterMaximumLength: Option[CardinalNumber],
  characterOctetLength: Option[CardinalNumber],
  characterSetCatalog: Option[SqlIdentifier],
  characterSetSchema: Option[SqlIdentifier],
  characterSetName: Option[SqlIdentifier],
  collationCatalog: Option[SqlIdentifier],
  collationSchema: Option[SqlIdentifier],
  collationName: Option[SqlIdentifier],
  numericPrecision: Option[CardinalNumber],
  numericPrecisionRadix: Option[CardinalNumber],
  numericScale: Option[CardinalNumber],
  datetimePrecision: Option[CardinalNumber],
  intervalType: Option[CharacterData],
  intervalPrecision: Option[CardinalNumber],
  domainDefault: Option[CharacterData],
  udtCatalog: Option[SqlIdentifier],
  udtSchema: Option[SqlIdentifier],
  udtName: Option[SqlIdentifier],
  scopeCatalog: Option[SqlIdentifier],
  scopeSchema: Option[SqlIdentifier],
  scopeName: Option[SqlIdentifier],
  maximumCardinality: Option[CardinalNumber],
  dtdIdentifier: Option[SqlIdentifier]
)

object DomainsViewRow {
  def rowParser(idx: Int): RowParser[DomainsViewRow] =
    RowParser[DomainsViewRow] { row =>
      Success(
        DomainsViewRow(
          domainCatalog = row[Option[SqlIdentifier]](idx + 0),
          domainSchema = row[Option[SqlIdentifier]](idx + 1),
          domainName = row[Option[SqlIdentifier]](idx + 2),
          dataType = row[Option[CharacterData]](idx + 3),
          characterMaximumLength = row[Option[CardinalNumber]](idx + 4),
          characterOctetLength = row[Option[CardinalNumber]](idx + 5),
          characterSetCatalog = row[Option[SqlIdentifier]](idx + 6),
          characterSetSchema = row[Option[SqlIdentifier]](idx + 7),
          characterSetName = row[Option[SqlIdentifier]](idx + 8),
          collationCatalog = row[Option[SqlIdentifier]](idx + 9),
          collationSchema = row[Option[SqlIdentifier]](idx + 10),
          collationName = row[Option[SqlIdentifier]](idx + 11),
          numericPrecision = row[Option[CardinalNumber]](idx + 12),
          numericPrecisionRadix = row[Option[CardinalNumber]](idx + 13),
          numericScale = row[Option[CardinalNumber]](idx + 14),
          datetimePrecision = row[Option[CardinalNumber]](idx + 15),
          intervalType = row[Option[CharacterData]](idx + 16),
          intervalPrecision = row[Option[CardinalNumber]](idx + 17),
          domainDefault = row[Option[CharacterData]](idx + 18),
          udtCatalog = row[Option[SqlIdentifier]](idx + 19),
          udtSchema = row[Option[SqlIdentifier]](idx + 20),
          udtName = row[Option[SqlIdentifier]](idx + 21),
          scopeCatalog = row[Option[SqlIdentifier]](idx + 22),
          scopeSchema = row[Option[SqlIdentifier]](idx + 23),
          scopeName = row[Option[SqlIdentifier]](idx + 24),
          maximumCardinality = row[Option[CardinalNumber]](idx + 25),
          dtdIdentifier = row[Option[SqlIdentifier]](idx + 26)
        )
      )
    }
  implicit val oFormat: OFormat[DomainsViewRow] = new OFormat[DomainsViewRow]{
    override def writes(o: DomainsViewRow): JsObject =
      Json.obj(
        "domain_catalog" -> o.domainCatalog,
        "domain_schema" -> o.domainSchema,
        "domain_name" -> o.domainName,
        "data_type" -> o.dataType,
        "character_maximum_length" -> o.characterMaximumLength,
        "character_octet_length" -> o.characterOctetLength,
        "character_set_catalog" -> o.characterSetCatalog,
        "character_set_schema" -> o.characterSetSchema,
        "character_set_name" -> o.characterSetName,
        "collation_catalog" -> o.collationCatalog,
        "collation_schema" -> o.collationSchema,
        "collation_name" -> o.collationName,
        "numeric_precision" -> o.numericPrecision,
        "numeric_precision_radix" -> o.numericPrecisionRadix,
        "numeric_scale" -> o.numericScale,
        "datetime_precision" -> o.datetimePrecision,
        "interval_type" -> o.intervalType,
        "interval_precision" -> o.intervalPrecision,
        "domain_default" -> o.domainDefault,
        "udt_catalog" -> o.udtCatalog,
        "udt_schema" -> o.udtSchema,
        "udt_name" -> o.udtName,
        "scope_catalog" -> o.scopeCatalog,
        "scope_schema" -> o.scopeSchema,
        "scope_name" -> o.scopeName,
        "maximum_cardinality" -> o.maximumCardinality,
        "dtd_identifier" -> o.dtdIdentifier
      )
  
    override def reads(json: JsValue): JsResult[DomainsViewRow] = {
      JsResult.fromTry(
        Try(
          DomainsViewRow(
            domainCatalog = json.\("domain_catalog").toOption.map(_.as[SqlIdentifier]),
            domainSchema = json.\("domain_schema").toOption.map(_.as[SqlIdentifier]),
            domainName = json.\("domain_name").toOption.map(_.as[SqlIdentifier]),
            dataType = json.\("data_type").toOption.map(_.as[CharacterData]),
            characterMaximumLength = json.\("character_maximum_length").toOption.map(_.as[CardinalNumber]),
            characterOctetLength = json.\("character_octet_length").toOption.map(_.as[CardinalNumber]),
            characterSetCatalog = json.\("character_set_catalog").toOption.map(_.as[SqlIdentifier]),
            characterSetSchema = json.\("character_set_schema").toOption.map(_.as[SqlIdentifier]),
            characterSetName = json.\("character_set_name").toOption.map(_.as[SqlIdentifier]),
            collationCatalog = json.\("collation_catalog").toOption.map(_.as[SqlIdentifier]),
            collationSchema = json.\("collation_schema").toOption.map(_.as[SqlIdentifier]),
            collationName = json.\("collation_name").toOption.map(_.as[SqlIdentifier]),
            numericPrecision = json.\("numeric_precision").toOption.map(_.as[CardinalNumber]),
            numericPrecisionRadix = json.\("numeric_precision_radix").toOption.map(_.as[CardinalNumber]),
            numericScale = json.\("numeric_scale").toOption.map(_.as[CardinalNumber]),
            datetimePrecision = json.\("datetime_precision").toOption.map(_.as[CardinalNumber]),
            intervalType = json.\("interval_type").toOption.map(_.as[CharacterData]),
            intervalPrecision = json.\("interval_precision").toOption.map(_.as[CardinalNumber]),
            domainDefault = json.\("domain_default").toOption.map(_.as[CharacterData]),
            udtCatalog = json.\("udt_catalog").toOption.map(_.as[SqlIdentifier]),
            udtSchema = json.\("udt_schema").toOption.map(_.as[SqlIdentifier]),
            udtName = json.\("udt_name").toOption.map(_.as[SqlIdentifier]),
            scopeCatalog = json.\("scope_catalog").toOption.map(_.as[SqlIdentifier]),
            scopeSchema = json.\("scope_schema").toOption.map(_.as[SqlIdentifier]),
            scopeName = json.\("scope_name").toOption.map(_.as[SqlIdentifier]),
            maximumCardinality = json.\("maximum_cardinality").toOption.map(_.as[CardinalNumber]),
            dtdIdentifier = json.\("dtd_identifier").toOption.map(_.as[SqlIdentifier])
          )
        )
      )
    }
  }
}