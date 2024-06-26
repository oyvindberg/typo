/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productproductphoto

import adventureworks.production.product.ProductId
import adventureworks.production.productphoto.ProductphotoId
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Type for the composite primary key of table `production.productproductphoto` */
case class ProductproductphotoId(
  productid: ProductId,
  productphotoid: ProductphotoId
)
object ProductproductphotoId {
  implicit lazy val jsonDecoder: JsonDecoder[ProductproductphotoId] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val productid = jsonObj.get("productid").toRight("Missing field 'productid'").flatMap(_.as(ProductId.jsonDecoder))
    val productphotoid = jsonObj.get("productphotoid").toRight("Missing field 'productphotoid'").flatMap(_.as(ProductphotoId.jsonDecoder))
    if (productid.isRight && productphotoid.isRight)
      Right(ProductproductphotoId(productid = productid.toOption.get, productphotoid = productphotoid.toOption.get))
    else Left(List[Either[String, Any]](productid, productphotoid).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[ProductproductphotoId] = new JsonEncoder[ProductproductphotoId] {
    override def unsafeEncode(a: ProductproductphotoId, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""productid":""")
      ProductId.jsonEncoder.unsafeEncode(a.productid, indent, out)
      out.write(",")
      out.write(""""productphotoid":""")
      ProductphotoId.jsonEncoder.unsafeEncode(a.productphotoid, indent, out)
      out.write("}")
    }
  }
  implicit lazy val ordering: Ordering[ProductproductphotoId] = Ordering.by(x => (x.productid, x.productphotoid))
}
