/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pmi

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.illustration.IllustrationId
import adventureworks.production.productmodel.ProductmodelId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.Structure.Relation

class PmiViewStructure[Row](val prefix: Option[String], val extract: Row => PmiViewRow, val merge: (Row, PmiViewRow) => Row)
  extends Relation[PmiViewFields, PmiViewRow, Row]
    with PmiViewFields[Row] { outer =>

  override val productmodelid = new Field[ProductmodelId, Row](prefix, "productmodelid", None, None)(x => extract(x).productmodelid, (row, value) => merge(row, extract(row).copy(productmodelid = value)))
  override val illustrationid = new Field[IllustrationId, Row](prefix, "illustrationid", None, None)(x => extract(x).illustrationid, (row, value) => merge(row, extract(row).copy(illustrationid = value)))
  override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), None)(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](productmodelid, illustrationid, modifieddate)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => PmiViewRow, merge: (NewRow, PmiViewRow) => NewRow): PmiViewStructure[NewRow] =
    new PmiViewStructure(prefix, extract, merge)
}