/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productsubcategory

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.productcategory.ProductcategoryId
import adventureworks.public.Name
import java.util.UUID
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

class ProductsubcategoryStructure[Row](val prefix: Option[String], val extract: Row => ProductsubcategoryRow, val merge: (Row, ProductsubcategoryRow) => Row)
  extends Relation[ProductsubcategoryFields, ProductsubcategoryRow, Row]
    with ProductsubcategoryFields[Row] { outer =>

  override val productsubcategoryid = new IdField[ProductsubcategoryId, Row](prefix, "productsubcategoryid", None, Some("int4"))(x => extract(x).productsubcategoryid, (row, value) => merge(row, extract(row).copy(productsubcategoryid = value)))
  override val productcategoryid = new Field[ProductcategoryId, Row](prefix, "productcategoryid", None, Some("int4"))(x => extract(x).productcategoryid, (row, value) => merge(row, extract(row).copy(productcategoryid = value)))
  override val name = new Field[Name, Row](prefix, "name", None, Some("varchar"))(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
  override val rowguid = new Field[UUID, Row](prefix, "rowguid", None, Some("uuid"))(x => extract(x).rowguid, (row, value) => merge(row, extract(row).copy(rowguid = value)))
  override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](productsubcategoryid, productcategoryid, name, rowguid, modifieddate)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => ProductsubcategoryRow, merge: (NewRow, ProductsubcategoryRow) => NewRow): ProductsubcategoryStructure[NewRow] =
    new ProductsubcategoryStructure(prefix, extract, merge)
}