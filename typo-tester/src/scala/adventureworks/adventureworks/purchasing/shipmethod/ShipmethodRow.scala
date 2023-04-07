/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package shipmethod

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

case class ShipmethodRow(
  shipmethodid: ShipmethodId /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"shipmethod","column_name":"shipmethodid","ordinal_position":1,"column_default":"nextval('purchasing.shipmethod_shipmethodid_seq'::regclass)","is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  name: String /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"shipmethod","column_name":"name","ordinal_position":2,"is_nullable":"NO","data_type":"character varying","character_maximum_length":50,"character_octet_length":200,"domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"Name","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  shipbase: BigDecimal /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"shipmethod","column_name":"shipbase","ordinal_position":3,"column_default":"0.00","is_nullable":"NO","data_type":"numeric","numeric_precision_radix":10,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"numeric","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  shiprate: BigDecimal /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"shipmethod","column_name":"shiprate","ordinal_position":4,"column_default":"0.00","is_nullable":"NO","data_type":"numeric","numeric_precision_radix":10,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"numeric","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  rowguid: UUID /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"shipmethod","column_name":"rowguid","ordinal_position":5,"column_default":"uuid_generate_v1()","is_nullable":"NO","data_type":"uuid","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"uuid","dtd_identifier":"5","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  modifieddate: LocalDateTime /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"shipmethod","column_name":"modifieddate","ordinal_position":6,"column_default":"now()","is_nullable":"NO","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"6","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
)

object ShipmethodRow {
  def rowParser(prefix: String): RowParser[ShipmethodRow] = { row =>
    Success(
      ShipmethodRow(
        shipmethodid = row[ShipmethodId](prefix + "shipmethodid"),
        name = row[String](prefix + "name"),
        shipbase = row[BigDecimal](prefix + "shipbase"),
        shiprate = row[BigDecimal](prefix + "shiprate"),
        rowguid = row[UUID](prefix + "rowguid"),
        modifieddate = row[LocalDateTime](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[ShipmethodRow] = new OFormat[ShipmethodRow]{
    override def writes(o: ShipmethodRow): JsObject =
      Json.obj(
        "shipmethodid" -> o.shipmethodid,
        "name" -> o.name,
        "shipbase" -> o.shipbase,
        "shiprate" -> o.shiprate,
        "rowguid" -> o.rowguid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[ShipmethodRow] = {
      JsResult.fromTry(
        Try(
          ShipmethodRow(
            shipmethodid = json.\("shipmethodid").as[ShipmethodId],
            name = json.\("name").as[String],
            shipbase = json.\("shipbase").as[BigDecimal],
            shiprate = json.\("shiprate").as[BigDecimal],
            rowguid = json.\("rowguid").as[UUID],
            modifieddate = json.\("modifieddate").as[LocalDateTime]
          )
        )
      )
    }
  }
}