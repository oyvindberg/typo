/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productmodel

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import java.util.UUID
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class ProductmodelRow(
  productmodelid: ProductmodelId /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productmodel","column_name":"productmodelid","ordinal_position":1,"column_default":"nextval('production.productmodel_productmodelid_seq'::regclass)","is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  name: String /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productmodel","column_name":"name","ordinal_position":2,"is_nullable":"NO","data_type":"character varying","character_maximum_length":50,"character_octet_length":200,"domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"Name","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  catalogdescription: Option[String] /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productmodel","column_name":"catalogdescription","ordinal_position":3,"is_nullable":"YES","data_type":"xml","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"xml","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  instructions: Option[String] /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productmodel","column_name":"instructions","ordinal_position":4,"is_nullable":"YES","data_type":"xml","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"xml","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  rowguid: UUID /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productmodel","column_name":"rowguid","ordinal_position":5,"column_default":"uuid_generate_v1()","is_nullable":"NO","data_type":"uuid","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"uuid","dtd_identifier":"5","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  modifieddate: LocalDateTime /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"productmodel","column_name":"modifieddate","ordinal_position":6,"column_default":"now()","is_nullable":"NO","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"6","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
)

object ProductmodelRow {
  def rowParser(prefix: String): RowParser[ProductmodelRow] = { row =>
    Success(
      ProductmodelRow(
        productmodelid = row[ProductmodelId](prefix + "productmodelid"),
        name = row[String](prefix + "name"),
        catalogdescription = row[Option[String]](prefix + "catalogdescription"),
        instructions = row[Option[String]](prefix + "instructions"),
        rowguid = row[UUID](prefix + "rowguid"),
        modifieddate = row[LocalDateTime](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[ProductmodelRow] = new OFormat[ProductmodelRow]{
    override def writes(o: ProductmodelRow): JsObject =
      Json.obj(
        "productmodelid" -> o.productmodelid,
        "name" -> o.name,
        "catalogdescription" -> o.catalogdescription,
        "instructions" -> o.instructions,
        "rowguid" -> o.rowguid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[ProductmodelRow] = {
      JsResult.fromTry(
        Try(
          ProductmodelRow(
            productmodelid = json.\("productmodelid").as[ProductmodelId],
            name = json.\("name").as[String],
            catalogdescription = json.\("catalogdescription").toOption.map(_.as[String]),
            instructions = json.\("instructions").toOption.map(_.as[String]),
            rowguid = json.\("rowguid").as[UUID],
            modifieddate = json.\("modifieddate").as[LocalDateTime]
          )
        )
      )
    }
  }
}