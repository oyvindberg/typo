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

case class ColumnsRow(
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  columnName: /* unknown nullability */ Option[String],
  ordinalPosition: /* unknown nullability */ Option[Int],
  columnDefault: /* unknown nullability */ Option[String],
  isNullable: /* unknown nullability */ Option[String],
  dataType: /* unknown nullability */ Option[String],
  characterMaximumLength: /* unknown nullability */ Option[Int],
  characterOctetLength: /* unknown nullability */ Option[Int],
  numericPrecision: /* unknown nullability */ Option[Int],
  numericPrecisionRadix: /* unknown nullability */ Option[Int],
  numericScale: /* unknown nullability */ Option[Int],
  datetimePrecision: /* unknown nullability */ Option[Int],
  intervalType: /* unknown nullability */ Option[String],
  intervalPrecision: /* unknown nullability */ Option[Int],
  characterSetCatalog: /* unknown nullability */ Option[String],
  characterSetSchema: /* unknown nullability */ Option[String],
  characterSetName: /* unknown nullability */ Option[String],
  collationCatalog: /* unknown nullability */ Option[String],
  collationSchema: /* unknown nullability */ Option[String],
  collationName: /* unknown nullability */ Option[String],
  domainCatalog: /* unknown nullability */ Option[String],
  domainSchema: /* unknown nullability */ Option[String],
  domainName: /* unknown nullability */ Option[String],
  udtCatalog: /* unknown nullability */ Option[String],
  udtSchema: /* unknown nullability */ Option[String],
  udtName: /* unknown nullability */ Option[String],
  scopeCatalog: /* unknown nullability */ Option[String],
  scopeSchema: /* unknown nullability */ Option[String],
  scopeName: /* unknown nullability */ Option[String],
  maximumCardinality: /* unknown nullability */ Option[Int],
  dtdIdentifier: /* unknown nullability */ Option[String],
  isSelfReferencing: /* unknown nullability */ Option[String],
  isIdentity: /* unknown nullability */ Option[String],
  identityGeneration: /* unknown nullability */ Option[String],
  identityStart: /* unknown nullability */ Option[String],
  identityIncrement: /* unknown nullability */ Option[String],
  identityMaximum: /* unknown nullability */ Option[String],
  identityMinimum: /* unknown nullability */ Option[String],
  identityCycle: /* unknown nullability */ Option[String],
  isGenerated: /* unknown nullability */ Option[String],
  generationExpression: /* unknown nullability */ Option[String],
  isUpdatable: /* unknown nullability */ Option[String]
)

object ColumnsRow {
  implicit val rowParser: RowParser[ColumnsRow] = { row =>
    Success(
      ColumnsRow(
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        columnName = row[/* unknown nullability */ Option[String]]("column_name"),
        ordinalPosition = row[/* unknown nullability */ Option[Int]]("ordinal_position"),
        columnDefault = row[/* unknown nullability */ Option[String]]("column_default"),
        isNullable = row[/* unknown nullability */ Option[String]]("is_nullable"),
        dataType = row[/* unknown nullability */ Option[String]]("data_type"),
        characterMaximumLength = row[/* unknown nullability */ Option[Int]]("character_maximum_length"),
        characterOctetLength = row[/* unknown nullability */ Option[Int]]("character_octet_length"),
        numericPrecision = row[/* unknown nullability */ Option[Int]]("numeric_precision"),
        numericPrecisionRadix = row[/* unknown nullability */ Option[Int]]("numeric_precision_radix"),
        numericScale = row[/* unknown nullability */ Option[Int]]("numeric_scale"),
        datetimePrecision = row[/* unknown nullability */ Option[Int]]("datetime_precision"),
        intervalType = row[/* unknown nullability */ Option[String]]("interval_type"),
        intervalPrecision = row[/* unknown nullability */ Option[Int]]("interval_precision"),
        characterSetCatalog = row[/* unknown nullability */ Option[String]]("character_set_catalog"),
        characterSetSchema = row[/* unknown nullability */ Option[String]]("character_set_schema"),
        characterSetName = row[/* unknown nullability */ Option[String]]("character_set_name"),
        collationCatalog = row[/* unknown nullability */ Option[String]]("collation_catalog"),
        collationSchema = row[/* unknown nullability */ Option[String]]("collation_schema"),
        collationName = row[/* unknown nullability */ Option[String]]("collation_name"),
        domainCatalog = row[/* unknown nullability */ Option[String]]("domain_catalog"),
        domainSchema = row[/* unknown nullability */ Option[String]]("domain_schema"),
        domainName = row[/* unknown nullability */ Option[String]]("domain_name"),
        udtCatalog = row[/* unknown nullability */ Option[String]]("udt_catalog"),
        udtSchema = row[/* unknown nullability */ Option[String]]("udt_schema"),
        udtName = row[/* unknown nullability */ Option[String]]("udt_name"),
        scopeCatalog = row[/* unknown nullability */ Option[String]]("scope_catalog"),
        scopeSchema = row[/* unknown nullability */ Option[String]]("scope_schema"),
        scopeName = row[/* unknown nullability */ Option[String]]("scope_name"),
        maximumCardinality = row[/* unknown nullability */ Option[Int]]("maximum_cardinality"),
        dtdIdentifier = row[/* unknown nullability */ Option[String]]("dtd_identifier"),
        isSelfReferencing = row[/* unknown nullability */ Option[String]]("is_self_referencing"),
        isIdentity = row[/* unknown nullability */ Option[String]]("is_identity"),
        identityGeneration = row[/* unknown nullability */ Option[String]]("identity_generation"),
        identityStart = row[/* unknown nullability */ Option[String]]("identity_start"),
        identityIncrement = row[/* unknown nullability */ Option[String]]("identity_increment"),
        identityMaximum = row[/* unknown nullability */ Option[String]]("identity_maximum"),
        identityMinimum = row[/* unknown nullability */ Option[String]]("identity_minimum"),
        identityCycle = row[/* unknown nullability */ Option[String]]("identity_cycle"),
        isGenerated = row[/* unknown nullability */ Option[String]]("is_generated"),
        generationExpression = row[/* unknown nullability */ Option[String]]("generation_expression"),
        isUpdatable = row[/* unknown nullability */ Option[String]]("is_updatable")
      )
    )
  }

  implicit val oFormat: OFormat[ColumnsRow] = new OFormat[ColumnsRow]{
    override def writes(o: ColumnsRow): JsObject =
      Json.obj(
        "table_catalog" -> o.tableCatalog,
      "table_schema" -> o.tableSchema,
      "table_name" -> o.tableName,
      "column_name" -> o.columnName,
      "ordinal_position" -> o.ordinalPosition,
      "column_default" -> o.columnDefault,
      "is_nullable" -> o.isNullable,
      "data_type" -> o.dataType,
      "character_maximum_length" -> o.characterMaximumLength,
      "character_octet_length" -> o.characterOctetLength,
      "numeric_precision" -> o.numericPrecision,
      "numeric_precision_radix" -> o.numericPrecisionRadix,
      "numeric_scale" -> o.numericScale,
      "datetime_precision" -> o.datetimePrecision,
      "interval_type" -> o.intervalType,
      "interval_precision" -> o.intervalPrecision,
      "character_set_catalog" -> o.characterSetCatalog,
      "character_set_schema" -> o.characterSetSchema,
      "character_set_name" -> o.characterSetName,
      "collation_catalog" -> o.collationCatalog,
      "collation_schema" -> o.collationSchema,
      "collation_name" -> o.collationName,
      "domain_catalog" -> o.domainCatalog,
      "domain_schema" -> o.domainSchema,
      "domain_name" -> o.domainName,
      "udt_catalog" -> o.udtCatalog,
      "udt_schema" -> o.udtSchema,
      "udt_name" -> o.udtName,
      "scope_catalog" -> o.scopeCatalog,
      "scope_schema" -> o.scopeSchema,
      "scope_name" -> o.scopeName,
      "maximum_cardinality" -> o.maximumCardinality,
      "dtd_identifier" -> o.dtdIdentifier,
      "is_self_referencing" -> o.isSelfReferencing,
      "is_identity" -> o.isIdentity,
      "identity_generation" -> o.identityGeneration,
      "identity_start" -> o.identityStart,
      "identity_increment" -> o.identityIncrement,
      "identity_maximum" -> o.identityMaximum,
      "identity_minimum" -> o.identityMinimum,
      "identity_cycle" -> o.identityCycle,
      "is_generated" -> o.isGenerated,
      "generation_expression" -> o.generationExpression,
      "is_updatable" -> o.isUpdatable
      )

    override def reads(json: JsValue): JsResult[ColumnsRow] = {
      JsResult.fromTry(
        Try(
          ColumnsRow(
            tableCatalog = json.\("table_catalog").toOption.map(_.as[String]),
            tableSchema = json.\("table_schema").toOption.map(_.as[String]),
            tableName = json.\("table_name").toOption.map(_.as[String]),
            columnName = json.\("column_name").toOption.map(_.as[String]),
            ordinalPosition = json.\("ordinal_position").toOption.map(_.as[Int]),
            columnDefault = json.\("column_default").toOption.map(_.as[String]),
            isNullable = json.\("is_nullable").toOption.map(_.as[String]),
            dataType = json.\("data_type").toOption.map(_.as[String]),
            characterMaximumLength = json.\("character_maximum_length").toOption.map(_.as[Int]),
            characterOctetLength = json.\("character_octet_length").toOption.map(_.as[Int]),
            numericPrecision = json.\("numeric_precision").toOption.map(_.as[Int]),
            numericPrecisionRadix = json.\("numeric_precision_radix").toOption.map(_.as[Int]),
            numericScale = json.\("numeric_scale").toOption.map(_.as[Int]),
            datetimePrecision = json.\("datetime_precision").toOption.map(_.as[Int]),
            intervalType = json.\("interval_type").toOption.map(_.as[String]),
            intervalPrecision = json.\("interval_precision").toOption.map(_.as[Int]),
            characterSetCatalog = json.\("character_set_catalog").toOption.map(_.as[String]),
            characterSetSchema = json.\("character_set_schema").toOption.map(_.as[String]),
            characterSetName = json.\("character_set_name").toOption.map(_.as[String]),
            collationCatalog = json.\("collation_catalog").toOption.map(_.as[String]),
            collationSchema = json.\("collation_schema").toOption.map(_.as[String]),
            collationName = json.\("collation_name").toOption.map(_.as[String]),
            domainCatalog = json.\("domain_catalog").toOption.map(_.as[String]),
            domainSchema = json.\("domain_schema").toOption.map(_.as[String]),
            domainName = json.\("domain_name").toOption.map(_.as[String]),
            udtCatalog = json.\("udt_catalog").toOption.map(_.as[String]),
            udtSchema = json.\("udt_schema").toOption.map(_.as[String]),
            udtName = json.\("udt_name").toOption.map(_.as[String]),
            scopeCatalog = json.\("scope_catalog").toOption.map(_.as[String]),
            scopeSchema = json.\("scope_schema").toOption.map(_.as[String]),
            scopeName = json.\("scope_name").toOption.map(_.as[String]),
            maximumCardinality = json.\("maximum_cardinality").toOption.map(_.as[Int]),
            dtdIdentifier = json.\("dtd_identifier").toOption.map(_.as[String]),
            isSelfReferencing = json.\("is_self_referencing").toOption.map(_.as[String]),
            isIdentity = json.\("is_identity").toOption.map(_.as[String]),
            identityGeneration = json.\("identity_generation").toOption.map(_.as[String]),
            identityStart = json.\("identity_start").toOption.map(_.as[String]),
            identityIncrement = json.\("identity_increment").toOption.map(_.as[String]),
            identityMaximum = json.\("identity_maximum").toOption.map(_.as[String]),
            identityMinimum = json.\("identity_minimum").toOption.map(_.as[String]),
            identityCycle = json.\("identity_cycle").toOption.map(_.as[String]),
            isGenerated = json.\("is_generated").toOption.map(_.as[String]),
            generationExpression = json.\("generation_expression").toOption.map(_.as[String]),
            isUpdatable = json.\("is_updatable").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
