/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package data_type_privileges

import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

class DataTypePrivilegesViewStructure[Row](val prefix: Option[String], val extract: Row => DataTypePrivilegesViewRow, val merge: (Row, DataTypePrivilegesViewRow) => Row)
  extends Relation[DataTypePrivilegesViewFields, DataTypePrivilegesViewRow, Row]
    with DataTypePrivilegesViewFields[Row] { outer =>

  override val objectCatalog = new OptField[String, Row](prefix, "object_catalog", None, None)(x => extract(x).objectCatalog, (row, value) => merge(row, extract(row).copy(objectCatalog = value)))
  override val objectSchema = new OptField[String, Row](prefix, "object_schema", None, None)(x => extract(x).objectSchema, (row, value) => merge(row, extract(row).copy(objectSchema = value)))
  override val objectName = new OptField[String, Row](prefix, "object_name", None, None)(x => extract(x).objectName, (row, value) => merge(row, extract(row).copy(objectName = value)))
  override val objectType = new OptField[String, Row](prefix, "object_type", None, None)(x => extract(x).objectType, (row, value) => merge(row, extract(row).copy(objectType = value)))
  override val dtdIdentifier = new OptField[String, Row](prefix, "dtd_identifier", None, None)(x => extract(x).dtdIdentifier, (row, value) => merge(row, extract(row).copy(dtdIdentifier = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](objectCatalog, objectSchema, objectName, objectType, dtdIdentifier)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => DataTypePrivilegesViewRow, merge: (NewRow, DataTypePrivilegesViewRow) => NewRow): DataTypePrivilegesViewStructure[NewRow] =
    new DataTypePrivilegesViewStructure(prefix, extract, merge)
}