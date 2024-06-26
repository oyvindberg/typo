/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productsubcategory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.production.productcategory.ProductcategoryId
import adventureworks.public.Name
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import scala.collection.immutable.ListMap
import scala.util.Try

/** Table: production.productsubcategory
    Product subcategories. See ProductCategory table.
    Primary key: productsubcategoryid */
case class ProductsubcategoryRow(
  /** Primary key for ProductSubcategory records.
      Default: nextval('production.productsubcategory_productsubcategoryid_seq'::regclass) */
  productsubcategoryid: ProductsubcategoryId,
  /** Product category identification number. Foreign key to ProductCategory.ProductCategoryID.
      Points to [[productcategory.ProductcategoryRow.productcategoryid]] */
  productcategoryid: ProductcategoryId,
  /** Subcategory description. */
  name: Name,
  /** Default: uuid_generate_v1() */
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = productsubcategoryid
   def toUnsavedRow(productsubcategoryid: Defaulted[ProductsubcategoryId], rowguid: Defaulted[TypoUUID] = Defaulted.Provided(this.rowguid), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): ProductsubcategoryRowUnsaved =
     ProductsubcategoryRowUnsaved(productcategoryid, name, productsubcategoryid, rowguid, modifieddate)
 }

object ProductsubcategoryRow {
  implicit lazy val reads: Reads[ProductsubcategoryRow] = Reads[ProductsubcategoryRow](json => JsResult.fromTry(
      Try(
        ProductsubcategoryRow(
          productsubcategoryid = json.\("productsubcategoryid").as(ProductsubcategoryId.reads),
          productcategoryid = json.\("productcategoryid").as(ProductcategoryId.reads),
          name = json.\("name").as(Name.reads),
          rowguid = json.\("rowguid").as(TypoUUID.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[ProductsubcategoryRow] = RowParser[ProductsubcategoryRow] { row =>
    Success(
      ProductsubcategoryRow(
        productsubcategoryid = row(idx + 0)(ProductsubcategoryId.column),
        productcategoryid = row(idx + 1)(ProductcategoryId.column),
        name = row(idx + 2)(Name.column),
        rowguid = row(idx + 3)(TypoUUID.column),
        modifieddate = row(idx + 4)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val text: Text[ProductsubcategoryRow] = Text.instance[ProductsubcategoryRow]{ (row, sb) =>
    ProductsubcategoryId.text.unsafeEncode(row.productsubcategoryid, sb)
    sb.append(Text.DELIMETER)
    ProductcategoryId.text.unsafeEncode(row.productcategoryid, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val writes: OWrites[ProductsubcategoryRow] = OWrites[ProductsubcategoryRow](o =>
    new JsObject(ListMap[String, JsValue](
      "productsubcategoryid" -> ProductsubcategoryId.writes.writes(o.productsubcategoryid),
      "productcategoryid" -> ProductcategoryId.writes.writes(o.productcategoryid),
      "name" -> Name.writes.writes(o.name),
      "rowguid" -> TypoUUID.writes.writes(o.rowguid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}
