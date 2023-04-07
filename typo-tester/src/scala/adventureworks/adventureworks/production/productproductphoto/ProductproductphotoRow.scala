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
import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class ProductproductphotoRow(
  /** Points to [[product.ProductRow.productid]] */
  productid: ProductId /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productproductphoto","column_name":"productid","ordinal_position":1,"is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[productphoto.ProductphotoRow.productphotoid]] */
  productphotoid: ProductphotoId /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productproductphoto","column_name":"productphotoid","ordinal_position":2,"is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  primary: Boolean /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productproductphoto","column_name":"primary","ordinal_position":3,"column_default":"false","is_nullable":"NO","data_type":"boolean","domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"Flag","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"bool","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  modifieddate: LocalDateTime /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productproductphoto","column_name":"modifieddate","ordinal_position":4,"column_default":"now()","is_nullable":"NO","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
){
   val compositeId: ProductproductphotoId = ProductproductphotoId(productid, productphotoid)
 }

object ProductproductphotoRow {
  def rowParser(prefix: String): RowParser[ProductproductphotoRow] = { row =>
    Success(
      ProductproductphotoRow(
        productid = row[ProductId](prefix + "productid"),
        productphotoid = row[ProductphotoId](prefix + "productphotoid"),
        primary = row[Boolean](prefix + "primary"),
        modifieddate = row[LocalDateTime](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[ProductproductphotoRow] = new OFormat[ProductproductphotoRow]{
    override def writes(o: ProductproductphotoRow): JsObject =
      Json.obj(
        "productid" -> o.productid,
        "productphotoid" -> o.productphotoid,
        "primary" -> o.primary,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[ProductproductphotoRow] = {
      JsResult.fromTry(
        Try(
          ProductproductphotoRow(
            productid = json.\("productid").as[ProductId],
            productphotoid = json.\("productphotoid").as[ProductphotoId],
            primary = json.\("primary").as[Boolean],
            modifieddate = json.\("modifieddate").as[LocalDateTime]
          )
        )
      )
    }
  }
}