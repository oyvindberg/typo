/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package attributes

import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

class AttributesViewStructure[Row](val prefix: Option[String], val extract: Row => AttributesViewRow, val merge: (Row, AttributesViewRow) => Row)
  extends Relation[AttributesViewFields, AttributesViewRow, Row]
    with AttributesViewFields[Row] { outer =>

  override val udtCatalog = new OptField[String, Row](prefix, "udt_catalog", None, None)(x => extract(x).udtCatalog, (row, value) => merge(row, extract(row).copy(udtCatalog = value)))
  override val udtSchema = new OptField[String, Row](prefix, "udt_schema", None, None)(x => extract(x).udtSchema, (row, value) => merge(row, extract(row).copy(udtSchema = value)))
  override val udtName = new OptField[String, Row](prefix, "udt_name", None, None)(x => extract(x).udtName, (row, value) => merge(row, extract(row).copy(udtName = value)))
  override val attributeName = new OptField[String, Row](prefix, "attribute_name", None, None)(x => extract(x).attributeName, (row, value) => merge(row, extract(row).copy(attributeName = value)))
  override val ordinalPosition = new OptField[Int, Row](prefix, "ordinal_position", None, None)(x => extract(x).ordinalPosition, (row, value) => merge(row, extract(row).copy(ordinalPosition = value)))
  override val attributeDefault = new OptField[String, Row](prefix, "attribute_default", None, None)(x => extract(x).attributeDefault, (row, value) => merge(row, extract(row).copy(attributeDefault = value)))
  override val isNullable = new OptField[/* max 3 chars */ String, Row](prefix, "is_nullable", None, None)(x => extract(x).isNullable, (row, value) => merge(row, extract(row).copy(isNullable = value)))
  override val dataType = new OptField[String, Row](prefix, "data_type", None, None)(x => extract(x).dataType, (row, value) => merge(row, extract(row).copy(dataType = value)))
  override val characterMaximumLength = new OptField[Int, Row](prefix, "character_maximum_length", None, None)(x => extract(x).characterMaximumLength, (row, value) => merge(row, extract(row).copy(characterMaximumLength = value)))
  override val characterOctetLength = new OptField[Int, Row](prefix, "character_octet_length", None, None)(x => extract(x).characterOctetLength, (row, value) => merge(row, extract(row).copy(characterOctetLength = value)))
  override val characterSetCatalog = new OptField[String, Row](prefix, "character_set_catalog", None, None)(x => extract(x).characterSetCatalog, (row, value) => merge(row, extract(row).copy(characterSetCatalog = value)))
  override val characterSetSchema = new OptField[String, Row](prefix, "character_set_schema", None, None)(x => extract(x).characterSetSchema, (row, value) => merge(row, extract(row).copy(characterSetSchema = value)))
  override val characterSetName = new OptField[String, Row](prefix, "character_set_name", None, None)(x => extract(x).characterSetName, (row, value) => merge(row, extract(row).copy(characterSetName = value)))
  override val collationCatalog = new OptField[String, Row](prefix, "collation_catalog", None, None)(x => extract(x).collationCatalog, (row, value) => merge(row, extract(row).copy(collationCatalog = value)))
  override val collationSchema = new OptField[String, Row](prefix, "collation_schema", None, None)(x => extract(x).collationSchema, (row, value) => merge(row, extract(row).copy(collationSchema = value)))
  override val collationName = new OptField[String, Row](prefix, "collation_name", None, None)(x => extract(x).collationName, (row, value) => merge(row, extract(row).copy(collationName = value)))
  override val numericPrecision = new OptField[Int, Row](prefix, "numeric_precision", None, None)(x => extract(x).numericPrecision, (row, value) => merge(row, extract(row).copy(numericPrecision = value)))
  override val numericPrecisionRadix = new OptField[Int, Row](prefix, "numeric_precision_radix", None, None)(x => extract(x).numericPrecisionRadix, (row, value) => merge(row, extract(row).copy(numericPrecisionRadix = value)))
  override val numericScale = new OptField[Int, Row](prefix, "numeric_scale", None, None)(x => extract(x).numericScale, (row, value) => merge(row, extract(row).copy(numericScale = value)))
  override val datetimePrecision = new OptField[Int, Row](prefix, "datetime_precision", None, None)(x => extract(x).datetimePrecision, (row, value) => merge(row, extract(row).copy(datetimePrecision = value)))
  override val intervalType = new OptField[String, Row](prefix, "interval_type", None, None)(x => extract(x).intervalType, (row, value) => merge(row, extract(row).copy(intervalType = value)))
  override val intervalPrecision = new OptField[Int, Row](prefix, "interval_precision", None, None)(x => extract(x).intervalPrecision, (row, value) => merge(row, extract(row).copy(intervalPrecision = value)))
  override val attributeUdtCatalog = new OptField[String, Row](prefix, "attribute_udt_catalog", None, None)(x => extract(x).attributeUdtCatalog, (row, value) => merge(row, extract(row).copy(attributeUdtCatalog = value)))
  override val attributeUdtSchema = new OptField[String, Row](prefix, "attribute_udt_schema", None, None)(x => extract(x).attributeUdtSchema, (row, value) => merge(row, extract(row).copy(attributeUdtSchema = value)))
  override val attributeUdtName = new OptField[String, Row](prefix, "attribute_udt_name", None, None)(x => extract(x).attributeUdtName, (row, value) => merge(row, extract(row).copy(attributeUdtName = value)))
  override val scopeCatalog = new OptField[String, Row](prefix, "scope_catalog", None, None)(x => extract(x).scopeCatalog, (row, value) => merge(row, extract(row).copy(scopeCatalog = value)))
  override val scopeSchema = new OptField[String, Row](prefix, "scope_schema", None, None)(x => extract(x).scopeSchema, (row, value) => merge(row, extract(row).copy(scopeSchema = value)))
  override val scopeName = new OptField[String, Row](prefix, "scope_name", None, None)(x => extract(x).scopeName, (row, value) => merge(row, extract(row).copy(scopeName = value)))
  override val maximumCardinality = new OptField[Int, Row](prefix, "maximum_cardinality", None, None)(x => extract(x).maximumCardinality, (row, value) => merge(row, extract(row).copy(maximumCardinality = value)))
  override val dtdIdentifier = new OptField[String, Row](prefix, "dtd_identifier", None, None)(x => extract(x).dtdIdentifier, (row, value) => merge(row, extract(row).copy(dtdIdentifier = value)))
  override val isDerivedReferenceAttribute = new OptField[/* max 3 chars */ String, Row](prefix, "is_derived_reference_attribute", None, None)(x => extract(x).isDerivedReferenceAttribute, (row, value) => merge(row, extract(row).copy(isDerivedReferenceAttribute = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](udtCatalog, udtSchema, udtName, attributeName, ordinalPosition, attributeDefault, isNullable, dataType, characterMaximumLength, characterOctetLength, characterSetCatalog, characterSetSchema, characterSetName, collationCatalog, collationSchema, collationName, numericPrecision, numericPrecisionRadix, numericScale, datetimePrecision, intervalType, intervalPrecision, attributeUdtCatalog, attributeUdtSchema, attributeUdtName, scopeCatalog, scopeSchema, scopeName, maximumCardinality, dtdIdentifier, isDerivedReferenceAttribute)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => AttributesViewRow, merge: (NewRow, AttributesViewRow) => NewRow): AttributesViewStructure[NewRow] =
    new AttributesViewStructure(prefix, extract, merge)
}