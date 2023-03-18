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

  implicit val oFormat: OFormat[UserDefinedTypesRow] = new OFormat[UserDefinedTypesRow]{
    override def writes(o: UserDefinedTypesRow): JsObject =
      Json.obj(
        "user_defined_type_catalog" -> o.userDefinedTypeCatalog,
      "user_defined_type_schema" -> o.userDefinedTypeSchema,
      "user_defined_type_name" -> o.userDefinedTypeName,
      "user_defined_type_category" -> o.userDefinedTypeCategory,
      "is_instantiable" -> o.isInstantiable,
      "is_final" -> o.isFinal,
      "ordering_form" -> o.orderingForm,
      "ordering_category" -> o.orderingCategory,
      "ordering_routine_catalog" -> o.orderingRoutineCatalog,
      "ordering_routine_schema" -> o.orderingRoutineSchema,
      "ordering_routine_name" -> o.orderingRoutineName,
      "reference_type" -> o.referenceType,
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
      "source_dtd_identifier" -> o.sourceDtdIdentifier,
      "ref_dtd_identifier" -> o.refDtdIdentifier
      )

    override def reads(json: JsValue): JsResult[UserDefinedTypesRow] = {
      JsResult.fromTry(
        Try(
          UserDefinedTypesRow(
            userDefinedTypeCatalog = json.\("user_defined_type_catalog").toOption.map(_.as[String]),
            userDefinedTypeSchema = json.\("user_defined_type_schema").toOption.map(_.as[String]),
            userDefinedTypeName = json.\("user_defined_type_name").toOption.map(_.as[String]),
            userDefinedTypeCategory = json.\("user_defined_type_category").toOption.map(_.as[String]),
            isInstantiable = json.\("is_instantiable").toOption.map(_.as[String]),
            isFinal = json.\("is_final").toOption.map(_.as[String]),
            orderingForm = json.\("ordering_form").toOption.map(_.as[String]),
            orderingCategory = json.\("ordering_category").toOption.map(_.as[String]),
            orderingRoutineCatalog = json.\("ordering_routine_catalog").toOption.map(_.as[String]),
            orderingRoutineSchema = json.\("ordering_routine_schema").toOption.map(_.as[String]),
            orderingRoutineName = json.\("ordering_routine_name").toOption.map(_.as[String]),
            referenceType = json.\("reference_type").toOption.map(_.as[String]),
            dataType = json.\("data_type").toOption.map(_.as[String]),
            characterMaximumLength = json.\("character_maximum_length").toOption.map(_.as[Int]),
            characterOctetLength = json.\("character_octet_length").toOption.map(_.as[Int]),
            characterSetCatalog = json.\("character_set_catalog").toOption.map(_.as[String]),
            characterSetSchema = json.\("character_set_schema").toOption.map(_.as[String]),
            characterSetName = json.\("character_set_name").toOption.map(_.as[String]),
            collationCatalog = json.\("collation_catalog").toOption.map(_.as[String]),
            collationSchema = json.\("collation_schema").toOption.map(_.as[String]),
            collationName = json.\("collation_name").toOption.map(_.as[String]),
            numericPrecision = json.\("numeric_precision").toOption.map(_.as[Int]),
            numericPrecisionRadix = json.\("numeric_precision_radix").toOption.map(_.as[Int]),
            numericScale = json.\("numeric_scale").toOption.map(_.as[Int]),
            datetimePrecision = json.\("datetime_precision").toOption.map(_.as[Int]),
            intervalType = json.\("interval_type").toOption.map(_.as[String]),
            intervalPrecision = json.\("interval_precision").toOption.map(_.as[Int]),
            sourceDtdIdentifier = json.\("source_dtd_identifier").toOption.map(_.as[String]),
            refDtdIdentifier = json.\("ref_dtd_identifier").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
