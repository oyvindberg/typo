package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class UserDefinedTypesRow(
  userDefinedTypeCatalog: /* unknown nullability */ Option[String],
  userDefinedTypeSchema: /* unknown nullability */ Option[String],
  userDefinedTypeName: /* unknown nullability */ Option[String],
  userDefinedTypeCategory: /* unknown nullability */ Option[String],
  isInstantiable: /* unknown nullability */ Option[String],
  isFinal: /* unknown nullability */ Option[String],
  orderingForm: /* unknown nullability */ Option[String],
  orderingCategory: /* unknown nullability */ Option[String],
  orderingRoutineCatalog: /* unknown nullability */ Option[String],
  orderingRoutineSchema: /* unknown nullability */ Option[String],
  orderingRoutineName: /* unknown nullability */ Option[String],
  referenceType: /* unknown nullability */ Option[String],
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
  sourceDtdIdentifier: /* unknown nullability */ Option[String],
  refDtdIdentifier: /* unknown nullability */ Option[String]
)

object UserDefinedTypesRow {
  implicit val rowParser: RowParser[UserDefinedTypesRow] = { row =>
    Success(
      UserDefinedTypesRow(
        userDefinedTypeCatalog = row[/* unknown nullability */ Option[String]]("user_defined_type_catalog"),
        userDefinedTypeSchema = row[/* unknown nullability */ Option[String]]("user_defined_type_schema"),
        userDefinedTypeName = row[/* unknown nullability */ Option[String]]("user_defined_type_name"),
        userDefinedTypeCategory = row[/* unknown nullability */ Option[String]]("user_defined_type_category"),
        isInstantiable = row[/* unknown nullability */ Option[String]]("is_instantiable"),
        isFinal = row[/* unknown nullability */ Option[String]]("is_final"),
        orderingForm = row[/* unknown nullability */ Option[String]]("ordering_form"),
        orderingCategory = row[/* unknown nullability */ Option[String]]("ordering_category"),
        orderingRoutineCatalog = row[/* unknown nullability */ Option[String]]("ordering_routine_catalog"),
        orderingRoutineSchema = row[/* unknown nullability */ Option[String]]("ordering_routine_schema"),
        orderingRoutineName = row[/* unknown nullability */ Option[String]]("ordering_routine_name"),
        referenceType = row[/* unknown nullability */ Option[String]]("reference_type"),
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
        sourceDtdIdentifier = row[/* unknown nullability */ Option[String]]("source_dtd_identifier"),
        refDtdIdentifier = row[/* unknown nullability */ Option[String]]("ref_dtd_identifier")
      )
    )
  }

  implicit val oFormat: OFormat[UserDefinedTypesRow] = Json.format
}
