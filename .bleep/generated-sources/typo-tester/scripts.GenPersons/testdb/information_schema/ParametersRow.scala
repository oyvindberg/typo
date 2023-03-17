package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ParametersRow(
  specificCatalog: /* unknown nullability */ Option[String],
  specificSchema: /* unknown nullability */ Option[String],
  specificName: /* unknown nullability */ Option[String],
  ordinalPosition: /* unknown nullability */ Option[Int],
  parameterMode: /* unknown nullability */ Option[String],
  isResult: /* unknown nullability */ Option[String],
  asLocator: /* unknown nullability */ Option[String],
  parameterName: /* unknown nullability */ Option[String],
  dataType: /* unknown nullability */ Option[String],
  characterMaximumLength: /* unknown nullability */ Option[Int],
  characterOctetLength: /* unknown nullability */ Option[Int],
  characterSetCatalog: /* unknown nullability */ Option[String],
  characterSetSchema: /* unknown nullability */ Option[String],
  characterSetName: /* unknown nullability */ Option[String],
  collationCatalog: /* unknown nullability */ Option[String],
  collationSchema: /* unknown nullability */ Option[String],
  collationName: /* unknown nullability */ Option[String],
  numericPrecision: /* unknown nullability */ Option[Int],
  numericPrecisionRadix: /* unknown nullability */ Option[Int],
  numericScale: /* unknown nullability */ Option[Int],
  datetimePrecision: /* unknown nullability */ Option[Int],
  intervalType: /* unknown nullability */ Option[String],
  intervalPrecision: /* unknown nullability */ Option[Int],
  udtCatalog: /* unknown nullability */ Option[String],
  udtSchema: /* unknown nullability */ Option[String],
  udtName: /* unknown nullability */ Option[String],
  scopeCatalog: /* unknown nullability */ Option[String],
  scopeSchema: /* unknown nullability */ Option[String],
  scopeName: /* unknown nullability */ Option[String],
  maximumCardinality: /* unknown nullability */ Option[Int],
  dtdIdentifier: /* unknown nullability */ Option[String],
  parameterDefault: /* unknown nullability */ Option[String]
)

object ParametersRow {
  implicit val rowParser: RowParser[ParametersRow] = { row =>
    Success(
      ParametersRow(
        specificCatalog = row[/* unknown nullability */ Option[String]]("specific_catalog"),
        specificSchema = row[/* unknown nullability */ Option[String]]("specific_schema"),
        specificName = row[/* unknown nullability */ Option[String]]("specific_name"),
        ordinalPosition = row[/* unknown nullability */ Option[Int]]("ordinal_position"),
        parameterMode = row[/* unknown nullability */ Option[String]]("parameter_mode"),
        isResult = row[/* unknown nullability */ Option[String]]("is_result"),
        asLocator = row[/* unknown nullability */ Option[String]]("as_locator"),
        parameterName = row[/* unknown nullability */ Option[String]]("parameter_name"),
        dataType = row[/* unknown nullability */ Option[String]]("data_type"),
        characterMaximumLength = row[/* unknown nullability */ Option[Int]]("character_maximum_length"),
        characterOctetLength = row[/* unknown nullability */ Option[Int]]("character_octet_length"),
        characterSetCatalog = row[/* unknown nullability */ Option[String]]("character_set_catalog"),
        characterSetSchema = row[/* unknown nullability */ Option[String]]("character_set_schema"),
        characterSetName = row[/* unknown nullability */ Option[String]]("character_set_name"),
        collationCatalog = row[/* unknown nullability */ Option[String]]("collation_catalog"),
        collationSchema = row[/* unknown nullability */ Option[String]]("collation_schema"),
        collationName = row[/* unknown nullability */ Option[String]]("collation_name"),
        numericPrecision = row[/* unknown nullability */ Option[Int]]("numeric_precision"),
        numericPrecisionRadix = row[/* unknown nullability */ Option[Int]]("numeric_precision_radix"),
        numericScale = row[/* unknown nullability */ Option[Int]]("numeric_scale"),
        datetimePrecision = row[/* unknown nullability */ Option[Int]]("datetime_precision"),
        intervalType = row[/* unknown nullability */ Option[String]]("interval_type"),
        intervalPrecision = row[/* unknown nullability */ Option[Int]]("interval_precision"),
        udtCatalog = row[/* unknown nullability */ Option[String]]("udt_catalog"),
        udtSchema = row[/* unknown nullability */ Option[String]]("udt_schema"),
        udtName = row[/* unknown nullability */ Option[String]]("udt_name"),
        scopeCatalog = row[/* unknown nullability */ Option[String]]("scope_catalog"),
        scopeSchema = row[/* unknown nullability */ Option[String]]("scope_schema"),
        scopeName = row[/* unknown nullability */ Option[String]]("scope_name"),
        maximumCardinality = row[/* unknown nullability */ Option[Int]]("maximum_cardinality"),
        dtdIdentifier = row[/* unknown nullability */ Option[String]]("dtd_identifier"),
        parameterDefault = row[/* unknown nullability */ Option[String]]("parameter_default")
      )
    )
  }

  implicit val oFormat: OFormat[ParametersRow] = Json.format
}
