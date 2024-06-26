/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productmodelproductdescriptionculture

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.culture.CultureId
import adventureworks.production.productdescription.ProductdescriptionId
import adventureworks.production.productmodel.ProductmodelId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Table: production.productmodelproductdescriptionculture
    Cross-reference table mapping product descriptions and the language the description is written in.
    Composite primary key: productmodelid, productdescriptionid, cultureid */
case class ProductmodelproductdescriptioncultureRow(
  /** Primary key. Foreign key to ProductModel.ProductModelID.
      Points to [[productmodel.ProductmodelRow.productmodelid]] */
  productmodelid: ProductmodelId,
  /** Primary key. Foreign key to ProductDescription.ProductDescriptionID.
      Points to [[productdescription.ProductdescriptionRow.productdescriptionid]] */
  productdescriptionid: ProductdescriptionId,
  /** Culture identification number. Foreign key to Culture.CultureID.
      Points to [[culture.CultureRow.cultureid]] */
  cultureid: CultureId,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val compositeId: ProductmodelproductdescriptioncultureId = ProductmodelproductdescriptioncultureId(productmodelid, productdescriptionid, cultureid)
   val id = compositeId
   def toUnsavedRow(modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): ProductmodelproductdescriptioncultureRowUnsaved =
     ProductmodelproductdescriptioncultureRowUnsaved(productmodelid, productdescriptionid, cultureid, modifieddate)
 }

object ProductmodelproductdescriptioncultureRow {
  def apply(compositeId: ProductmodelproductdescriptioncultureId, modifieddate: TypoLocalDateTime) =
    new ProductmodelproductdescriptioncultureRow(compositeId.productmodelid, compositeId.productdescriptionid, compositeId.cultureid, modifieddate)
  implicit lazy val jdbcDecoder: JdbcDecoder[ProductmodelproductdescriptioncultureRow] = new JdbcDecoder[ProductmodelproductdescriptioncultureRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, ProductmodelproductdescriptioncultureRow) =
      columIndex + 3 ->
        ProductmodelproductdescriptioncultureRow(
          productmodelid = ProductmodelId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          productdescriptionid = ProductdescriptionId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          cultureid = CultureId.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[ProductmodelproductdescriptioncultureRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val productmodelid = jsonObj.get("productmodelid").toRight("Missing field 'productmodelid'").flatMap(_.as(ProductmodelId.jsonDecoder))
    val productdescriptionid = jsonObj.get("productdescriptionid").toRight("Missing field 'productdescriptionid'").flatMap(_.as(ProductdescriptionId.jsonDecoder))
    val cultureid = jsonObj.get("cultureid").toRight("Missing field 'cultureid'").flatMap(_.as(CultureId.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (productmodelid.isRight && productdescriptionid.isRight && cultureid.isRight && modifieddate.isRight)
      Right(ProductmodelproductdescriptioncultureRow(productmodelid = productmodelid.toOption.get, productdescriptionid = productdescriptionid.toOption.get, cultureid = cultureid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](productmodelid, productdescriptionid, cultureid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[ProductmodelproductdescriptioncultureRow] = new JsonEncoder[ProductmodelproductdescriptioncultureRow] {
    override def unsafeEncode(a: ProductmodelproductdescriptioncultureRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""productmodelid":""")
      ProductmodelId.jsonEncoder.unsafeEncode(a.productmodelid, indent, out)
      out.write(",")
      out.write(""""productdescriptionid":""")
      ProductdescriptionId.jsonEncoder.unsafeEncode(a.productdescriptionid, indent, out)
      out.write(",")
      out.write(""""cultureid":""")
      CultureId.jsonEncoder.unsafeEncode(a.cultureid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[ProductmodelproductdescriptioncultureRow] = Text.instance[ProductmodelproductdescriptioncultureRow]{ (row, sb) =>
    ProductmodelId.text.unsafeEncode(row.productmodelid, sb)
    sb.append(Text.DELIMETER)
    ProductdescriptionId.text.unsafeEncode(row.productdescriptionid, sb)
    sb.append(Text.DELIMETER)
    CultureId.text.unsafeEncode(row.cultureid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
}
